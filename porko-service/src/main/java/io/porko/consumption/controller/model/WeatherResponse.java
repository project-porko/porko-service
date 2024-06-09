package io.porko.consumption.controller.model;

import java.math.BigDecimal;
import java.util.List;

public record WeatherResponse(
        String name,
        BigDecimal used,
        List<WeatherListResponse> weather
) {
    public WeatherResponse of (
            String name,
            BigDecimal used,
            List<WeatherListResponse> weather) {
        return new WeatherResponse(name, used, weather);
    }
}
