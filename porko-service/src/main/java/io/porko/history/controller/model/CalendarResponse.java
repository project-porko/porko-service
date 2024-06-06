package io.porko.history.controller.model;

import java.util.List;

public record CalendarResponse(
        List<CalendarDayResponse> day,
        List<CalendarWeekResponse> week
) {
}
