package io.porko.history.service;

import io.porko.budget.service.BudgetService;
import io.porko.consumption.domain.Weather;
import io.porko.history.controller.model.CalendarResponse;
import io.porko.history.controller.model.HistoryDetailResponse;
import io.porko.history.controller.model.HistoryListResponse;
import io.porko.history.controller.model.HistoryResponse;
import io.porko.history.domain.History;
import io.porko.history.repo.HistoryQueryRepo;
import io.porko.history.exception.HistoryErrorCode;
import io.porko.history.exception.HistoryException;
import io.porko.history.repo.HistoryRepo;
import io.porko.member.repo.MemberQueryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepo historyRepo;
    private final HistoryQueryRepo historyQueryRepo;
    private final MemberQueryRepo memberQueryRepo;
    private final BudgetService budgetService;

    public HistoryListResponse getThisMonthHistoryList(Long loginMemberId) {
        YearMonth thisMonth = YearMonth.now();
        LocalDate startDate = thisMonth.atDay(1);
        LocalDate endDate = thisMonth.atEndOfMonth();

        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    public HistoryListResponse getHistoryListByDate(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    public HistoryDetailResponse getHistoryDetail(Long historyId) {
        History history = historyRepo.findById(historyId)
                .orElseThrow(() -> new HistoryException(HistoryErrorCode.HISTORY_INVALID_DATE_RANGE, historyId));
        return HistoryDetailResponse.ofDetail(history);
    }

    @Transactional
    public HistoryResponse updateRegretStatus(Long historyId, Boolean regret) {
        History history = historyRepo.findById(historyId)
                .orElseThrow(() -> new HistoryException(HistoryErrorCode.HISTORY_NOT_FOUND ,historyId));
        history.updateRegret(regret);
        historyRepo.save(history);
        return HistoryResponse.of(history);
    }

    public HistoryListResponse fetchHistoryList(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        loadMemberById(loginMemberId);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        BigDecimal totalSpent = historyRepo.calcSpentCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);
        BigDecimal totalEarned = historyRepo.calcEarnedCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);

        List<History> histories = historyRepo.findByMemberIdAndUsedAtBetween(loginMemberId, startDateTime, endDateTime);

        List<HistoryResponse> historyResponses = histories.stream()
                .map(HistoryResponse::of)
                .collect(Collectors.toList());

        return HistoryListResponse.of(historyResponses, totalSpent, totalEarned);
    }

    private void loadMemberById(Long id) {
        memberQueryRepo.findMemberById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }
 
    public List<CalendarResponse> getCalendar(int year, int month, Long memberId) {
        YearMonth yearMonth = YearMonth.of(year, month);
        List<CalendarResponse> calendarResponseList = new ArrayList<>();

        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth;
        if (yearMonth.getYear() == LocalDate.now().getYear()
                && yearMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            lastDayOfMonth = LocalDate.now();
        } else {
            lastDayOfMonth = yearMonth.atEndOfMonth();
        }

        BigDecimal dailyUsedCost;
        BigDecimal dailyEarnedCost;
        BigDecimal dailyUsedRate;

        for (LocalDate date = firstDayOfMonth;
             date.isBefore(lastDayOfMonth) || date.isEqual(lastDayOfMonth);
             date = date.plusDays(1)) {
            dailyUsedCost = historyQueryRepo.calcDailyUsedCost(date, memberId)
                    .orElse(BigDecimal.ZERO)
                    .stripTrailingZeros();

            dailyEarnedCost = historyQueryRepo.calcDailyEarnedCost(date, memberId)
                    .orElse(BigDecimal.ZERO)
                    .stripTrailingZeros();


            BigDecimal dailyBudget = budgetService.calcDailyBudget(date, memberId);
            if (dailyBudget == BigDecimal.ZERO) {
                dailyUsedRate = BigDecimal.ZERO;
            } else {
                dailyUsedRate = dailyUsedCost.divide(dailyBudget, 2, RoundingMode.HALF_UP)
                        .abs()
                        .multiply(BigDecimal.valueOf(100))
                        .stripTrailingZeros();
            }

            calendarResponseList.add(CalendarResponse.of(
                    date,
                    dailyUsedCost,
                    dailyEarnedCost,
                    Weather.getWeatherByDailyUsed(dailyUsedRate).weatherImageNo));
        }

        return calendarResponseList;
    }
}
