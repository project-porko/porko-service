package io.porko.consumption.controller.model;

public record WeatherListResponse (
        Integer weatherImageNo,
        String weatherName,
        int count) {
    public static WeatherListResponse of(
            Integer weatherImageNo,
            String weatherName,
            int count) {
        return new WeatherListResponse(weatherImageNo, weatherName, count);
    }
}