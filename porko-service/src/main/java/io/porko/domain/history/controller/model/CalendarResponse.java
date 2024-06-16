package io.porko.domain.history.controller.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalendarResponse(
        String date,
        BigDecimal usedCost,
        BigDecimal earnedCost,
        Integer weatherImageNo) {
    public static CalendarResponse of(
            LocalDate localDate,
            BigDecimal usedCost,
            BigDecimal earnedCost,
            Integer weatherImageNo) {
        return new CalendarResponse(localDate.toString(), usedCost, earnedCost, weatherImageNo);
    }
}
