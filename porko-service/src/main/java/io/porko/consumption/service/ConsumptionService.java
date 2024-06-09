package io.porko.consumption.service;

import io.porko.consumption.controller.model.RegretResponse;
import io.porko.consumption.domain.RegretItem;
import io.porko.history.repo.HistoryQueryRepo;
import io.porko.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class ConsumptionService {
    private final HistoryQueryRepo historyQueryRepo;
    private final HistoryService historyService;

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
}
