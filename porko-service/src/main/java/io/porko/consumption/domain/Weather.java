package io.porko.consumption.domain;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public enum Weather {
    RAINBOW("무지출이 뜬 날", "rainbowURL"),
    SUN("지출 하늘이 맑은 날", "sunURL"),
    CLOUD("지출 구름이 낀 날", "cloudURL"),
    RAIN("지출 비가 내리는 날", "rainURL"),
    THUNDER("지출 번개가 치는 날", "thunderURL");

    public final String name;
    public final String imageUrl;

    public static Weather getWeatherByDailyUsed(BigDecimal dailyUsed) {
        if (dailyUsed.compareTo(BigDecimal.ZERO) == 0) {
            return RAINBOW;
        } else if (dailyUsed.compareTo(BigDecimal.valueOf(0)) > 0 && dailyUsed.compareTo(BigDecimal.valueOf(33)) < 0) {
            return SUN;
        } else if (dailyUsed.compareTo(BigDecimal.valueOf(33)) >= 0 && dailyUsed.compareTo(BigDecimal.valueOf(66)) < 0) {
            return CLOUD;
        } else if (dailyUsed.compareTo(BigDecimal.valueOf(66)) >= 0 && dailyUsed.compareTo(BigDecimal.valueOf(100)) < 0) {
            return RAIN;
        } else {
            return THUNDER;
        }
    }
}