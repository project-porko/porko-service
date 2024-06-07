package io.porko.history.controller.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalendarResponse(
        String date,
        BigDecimal usedCost,
        BigDecimal earnedCost,
        int weatherImageNo) {
    public static CalendarResponse of(
            LocalDate localDate,
            BigDecimal usedCost,
            BigDecimal earnedCost,
            int weatherImageNo) {
        return new CalendarResponse(localDate.toString(), usedCost, earnedCost, weatherImageNo);
    }
}
