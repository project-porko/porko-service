package io.porko.history.controller.model;

import java.math.BigDecimal;

public record CalendarWeekResponse(
        int week,
        BigDecimal usedCost,
        BigDecimal earnedCost) {
    public static CalendarWeekResponse of(
            int week,
            BigDecimal usedCost,
            BigDecimal earnedCost) {
        return new CalendarWeekResponse(week, usedCost, earnedCost);
    }
}