package io.porko.history.service;

import io.porko.budget.service.BudgetService;
import io.porko.consumption.domain.Weather;
import io.porko.history.controller.model.CalendarResponse;
import io.porko.history.controller.model.HistoryDetailResponse;
import io.porko.history.controller.model.HistoryListResponse;
import io.porko.history.controller.model.HistoryResponse;
import io.porko.history.domain.History;
import io.porko.history.exception.HistoryErrorCode;
import io.porko.history.exception.HistoryException;
import io.porko.history.repo.HistoryQueryRepo;
import io.porko.history.repo.HistoryRepo;
import io.porko.pagination.response.PageResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepo historyRepo;
    private final HistoryQueryRepo historyQueryRepo;
    private final BudgetService budgetService;

    public HistoryListResponse<History, HistoryResponse> getThisMonthHistoryList(Long loginMemberId, Pageable pageable) {
        YearMonth thisMonth = YearMonth.now();
        LocalDate startDate = thisMonth.atDay(1);
        LocalDate endDate = thisMonth.atEndOfMonth();

        return fetchHistoryList(loginMemberId, startDate, endDate, pageable);
    }

    public HistoryListResponse<History, HistoryResponse> getHistoryListByDate(Long loginMemberId, LocalDate startDate, LocalDate endDate,
        Pageable pageable) {
        return fetchHistoryList(loginMemberId, startDate, endDate, pageable);
    }

    public HistoryDetailResponse getHistoryDetail(Long historyId) {
        History history = historyRepo.findById(historyId)
            .orElseThrow(() -> new HistoryException(HistoryErrorCode.HISTORY_NOT_FOUND, historyId));
        return HistoryDetailResponse.ofDetail(history);
    }

    @Transactional
    public HistoryResponse updateRegretStatus(Long historyId, Boolean regret) {
        History history = historyRepo.findById(historyId)
            .orElseThrow(() -> new HistoryException(HistoryErrorCode.HISTORY_NOT_FOUND, historyId));
        history.updateRegret(regret);
        historyRepo.save(history);
        return HistoryResponse.of(history);
    }

    public HistoryListResponse<History, HistoryResponse> fetchHistoryList(Long loginMemberId, LocalDate startDate, LocalDate endDate,
        Pageable pageable) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        BigDecimal totalSpent = historyRepo.calcSpentCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);
        BigDecimal totalEarned = historyRepo.calcEarnedCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);

        Page<History> histories = historyRepo.findByMemberIdAndUsedAtBetweenOrderByUsedAtDesc(loginMemberId, startDateTime, endDateTime, pageable);

        List<HistoryResponse> historyResponses = histories.stream()
            .map(HistoryResponse::of)
            .collect(Collectors.toList());

        return HistoryListResponse.of(
            totalSpent,
            totalEarned,
            PageResponse.of(histories, historyResponses)
        );
    }

    public List<CalendarResponse> getCalendar(int year, int month, Long memberId) {
        YearMonth yearMonth = YearMonth.of(year, month);
        List<CalendarResponse> calendarResponseList = new ArrayList<>();

        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = getLastDayOfMonth(yearMonth);

        for (LocalDate date = firstDayOfMonth;
            date.isBefore(lastDayOfMonth) || date.isEqual(lastDayOfMonth);
            date = date.plusDays(1)) {

            BigDecimal dailyUsedCost = calcDailyUsedCost(date, memberId);
            BigDecimal dailyEarnedCost = calcDailyEarnedCost(date, memberId);

            calendarResponseList.add(
                CalendarResponse.of(
                    date,
                    dailyUsedCost,
                    dailyEarnedCost,
                    selectWeatherImage(date, memberId, calcDailyUsedCost(
                                date, memberId))
                )
            );
        }

        return calendarResponseList;
    }

    public LocalDate getLastDayOfMonth(YearMonth yearMonth) {
        if (yearMonth.getYear() == LocalDate.now().getYear()
            && yearMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            return LocalDate.now();
        } else {
            return yearMonth.atEndOfMonth();
        }
    }

    public BigDecimal calcDailyUsedCost(LocalDate date, Long memberId) {
        return historyQueryRepo.calcDailyUsedCost(date, memberId)
            .orElse(BigDecimal.ZERO)
            .stripTrailingZeros();
    }

    private BigDecimal calcDailyEarnedCost(LocalDate date, Long memberId) {
        return historyQueryRepo.calcDailyEarnedCost(date, memberId)
            .orElse(BigDecimal.ZERO)
            .stripTrailingZeros();
    }

    private BigDecimal calcDailyUsedRate(LocalDate date, Long memberId, BigDecimal dailyUsedCost) {
        return Optional.ofNullable(budgetService.calcDailyBudget(date, memberId))
            .map(dailyBudget -> {
                if (dailyBudget.equals(BigDecimal.ZERO)) {
                    return dailyBudget;
                } else {
                    return dailyUsedCost.divide(dailyBudget, 2, RoundingMode.HALF_UP)
                        .abs()
                        .multiply(BigDecimal.valueOf(100))
                        .stripTrailingZeros();
                }
            })
            .orElse(null);
    }

    public Integer selectWeatherImage(LocalDate date, Long memberId, BigDecimal dailyUsedCost) {
        return Optional.ofNullable(calcDailyUsedRate(date, memberId, dailyUsedCost))
                .map(dailyUsedRate -> Weather.getWeatherByDailyUsed(dailyUsedRate).weatherImageNo)
                .orElse(null);
    }
}
