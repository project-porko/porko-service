package io.porko.history.service;

import io.porko.consumption.domain.Weather;
import io.porko.history.controller.model.CalendarDayResponse;
import io.porko.history.controller.model.CalendarResponse;
import io.porko.history.controller.model.CalendarWeekResponse;
import io.porko.history.controller.model.HistoryResponse;
import io.porko.history.domain.History;
import io.porko.history.repo.HistoryQueryRepo;
import io.porko.history.repo.HistoryRepo;
import io.porko.member.repo.MemberQueryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
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

    public List<HistoryResponse> getThisMonthHistoryList(Long loginMemberId) {
        YearMonth thisMonth = YearMonth.now();
        LocalDate startDate = thisMonth.atDay(1);
        LocalDate endDate = thisMonth.atEndOfMonth();

        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    public List<HistoryResponse> getHistoryListByDate(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    public HistoryResponse getHistoryDetail( Long historyId) {
        History history = historyRepo.findById(historyId)
                .orElseThrow(() -> new RuntimeException("상세조회를 찾을수 없습니다." + historyId));
        return HistoryResponse.ofDetail(history);
    }

    private List<HistoryResponse> fetchHistoryList(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        loadMemberById(loginMemberId);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        BigDecimal totalSpent = historyRepo.calcUsedCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);
        BigDecimal totalEarned = historyRepo.calcEarnedCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);

        List<History> histories = historyRepo.findByMemberIdAndUsedAtBetween(loginMemberId, startDateTime, endDateTime);

        return histories.stream()
                .map(history -> HistoryResponse.of(history, totalSpent, totalEarned))
                .collect(Collectors.toList());
    }

    private void loadMemberById(Long id) {
        memberQueryRepo.findMemberById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }

    public CalendarResponse getCalendar(Integer year, Integer month, Long memberId) {
        YearMonth yearMonth = YearMonth.of(year, month);

        return new CalendarResponse(
                getCalendarDayResponseList(yearMonth, memberId),
                getCalendarWeekResponseList(yearMonth, memberId));
    }

    private List<CalendarDayResponse> getCalendarDayResponseList(YearMonth yearMonth, Long memberId) {
        List<CalendarDayResponse> calendarDayResponseList = new ArrayList<>();

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

        for (LocalDate date = firstDayOfMonth;
             date.isBefore(lastDayOfMonth) || date.isEqual(lastDayOfMonth);
             date = date.plusDays(1)) {
            dailyUsedCost = historyQueryRepo.calcDailyUsedCost(date, memberId)
                    .orElse(BigDecimal.ZERO)
                    .stripTrailingZeros();
            dailyEarnedCost = historyQueryRepo.calcDailyEarnedCost(date, memberId)
                    .orElse(BigDecimal.ZERO)
                    .stripTrailingZeros();

            calendarDayResponseList.add(CalendarDayResponse.of(
                    date,
                    dailyUsedCost,
                    dailyEarnedCost,
                    Weather.getWeatherByDailyUsed(dailyUsedCost).imageURL));
        }

        return calendarDayResponseList;
    }

    private List<CalendarWeekResponse> getCalendarWeekResponseList(YearMonth yearMonth, Long memberId) {
        List<CalendarWeekResponse> calendarWeekResponseList = new ArrayList<>();

        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth;

        if (yearMonth.getYear() == LocalDate.now().getYear()
                && yearMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            lastDayOfMonth = LocalDate.now();
        } else {
            lastDayOfMonth = yearMonth.atEndOfMonth();
        }

        int week = 1;
        int daysBetween = DayOfWeek.values().length - firstDayOfMonth.getDayOfWeek().getValue();
        calendarWeekResponseList.add(CalendarWeekResponse.of(
                    week++,
                    historyQueryRepo.calcUsedCostForPeriod(firstDayOfMonth, firstDayOfMonth.plusDays(daysBetween), memberId)
                            .orElse(BigDecimal.ZERO)
                            .stripTrailingZeros(),
                    historyQueryRepo.calcEarnedCostForPeriod(firstDayOfMonth, firstDayOfMonth.plusDays(daysBetween), memberId)
                            .orElse(BigDecimal.ZERO)
                            .stripTrailingZeros()));

        for (LocalDate date = firstDayOfMonth.plusDays(daysBetween + 1);
             date.isBefore(lastDayOfMonth) || date.isEqual(lastDayOfMonth);
             date = date.plusDays(DayOfWeek.values().length)) {
            calendarWeekResponseList.add(CalendarWeekResponse.of(
                    week++,
                    historyQueryRepo.calcUsedCostForPeriod(date, date.plusDays(DayOfWeek.values().length - 1), memberId)
                            .orElse(BigDecimal.ZERO)
                            .stripTrailingZeros(),
                    historyQueryRepo.calcEarnedCostForPeriod(date, date.plusDays(DayOfWeek.values().length - 1), memberId)
                            .orElse(BigDecimal.ZERO)
                            .stripTrailingZeros()));
        }

        daysBetween = lastDayOfMonth.getDayOfWeek().getValue();

        calendarWeekResponseList.add(CalendarWeekResponse.of(
                week,
                historyQueryRepo.calcUsedCostForPeriod(lastDayOfMonth.minusDays(daysBetween + 1), lastDayOfMonth, memberId)
                        .orElse(BigDecimal.ZERO)
                        .stripTrailingZeros(),
                historyQueryRepo.calcEarnedCostForPeriod(lastDayOfMonth.minusDays(daysBetween + 1), lastDayOfMonth, memberId)
                        .orElse(BigDecimal.ZERO)
                        .stripTrailingZeros()));


        return calendarWeekResponseList;
    }
}
