package io.porko.consumption.controller.model;

public record WeatherListResponse(Integer weatherImageNo, int count) {
    public WeatherListResponse of(Integer weatherImageNo, int count) {
        return new WeatherListResponse(weatherImageNo, count);
    }
}
