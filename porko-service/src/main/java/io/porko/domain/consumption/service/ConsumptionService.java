package io.porko.domain.consumption.service;

import io.porko.domain.budget.service.BudgetService;
import io.porko.domain.consumption.controller.model.RegretResponse;
import io.porko.domain.consumption.controller.model.WeatherListResponse;
import io.porko.domain.consumption.controller.model.WeatherResponse;
import io.porko.domain.consumption.domain.RegretItem;
import io.porko.domain.consumption.domain.Weather;
import io.porko.domain.history.repo.HistoryQueryRepo;
import io.porko.domain.history.service.HistoryService;
import io.porko.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumptionService {
    private final HistoryQueryRepo historyQueryRepo;
    private final HistoryService historyService;
    private final MemberService memberService;
    private final BudgetService budgetService;

    public RegretResponse makeRegretResponse(int year, int month, Long id) {
        YearMonth yearMonth = YearMonth.of(year, month);

        BigDecimal regretCost = calcRegretCost(yearMonth, id);

        return RegretResponse.of(
                RegretItem.getRegretItemBymonthlyUsedWithRegret(regretCost).regretItemImageNo,
                regretCost);
    }

    private BigDecimal calcRegretCost(YearMonth yearMonth, Long id) {
        LocalDateTime startDateTime = LocalDateTime.of(yearMonth.atDay(1), LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(historyService.getLastDayOfMonth(yearMonth), LocalTime.MAX);

        return historyQueryRepo.calcRegretCost(startDateTime, endDateTime, id)
                .orElse(BigDecimal.ZERO)
                .abs()
                .stripTrailingZeros();
    }

    public WeatherResponse makeWeatherResponse(int year, int month, Long id) {
        return WeatherResponse.of(
                memberService.findMemberById(id).getName(),
                budgetService.getBudget(year, month, id).used(),
                makeWeatherListResponse(
                        YearMonth.of(year, month),
                        id));
    }

    private List<WeatherListResponse> makeWeatherListResponse(YearMonth yearMonth, Long id) {
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = historyService.getLastDayOfMonth(yearMonth);

        List<WeatherListResponse> weatherList = new ArrayList<>();
        List<Integer> dailyWeather = new ArrayList<>();

        for (LocalDate date = firstDayOfMonth;
             date.isBefore(lastDayOfMonth) || date.isEqual(lastDayOfMonth);
             date = date.plusDays(1)) {
             dailyWeather.add(historyService.selectWeatherImage(
                    date, id, historyService.calcDailyUsedCost(
                    date, id)));
        }
        for (Weather weather : Weather.values()) {
            weatherList.add(
                    WeatherListResponse.of(
                            weather.weatherImageNo,
                            weather.weatherName,
                            countWeather(dailyWeather, weather.weatherImageNo)
                    ));
        }

        weatherList.sort(Comparator.comparing(WeatherListResponse::count).reversed());

        return weatherList;
    }

    private Integer countWeather(List<Integer> dailyWeather, Integer weather) {
        int count = 0;

        for (Integer day : dailyWeather) {
            if (day.equals(weather)) {
                count++;
            }
        }

        return count;
    }
}
