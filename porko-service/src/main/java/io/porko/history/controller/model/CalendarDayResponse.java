package io.porko.history.controller.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalendarDayResponse(
        String date,
        BigDecimal usedCost,
        BigDecimal earnedCost,
        String weatherURL) {
    public static CalendarDayResponse of(
            LocalDate localDate,
            BigDecimal usedCost,
            BigDecimal earnedCost,
            String weatherURL) {
        return new CalendarDayResponse(localDate.toString(), usedCost, earnedCost, weatherURL);
    }
}
