package io.porko.consumption.domain;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public enum RegretItem {
    COFFEE ("커피 한 잔"),
    HAMBURGER("햄버거 세트"),
    CHICKEN("치킨 두 마리"),
    SUSHI("스시 오마카세"),
    APPLE_PENCLE("애플 펜슬"),
    APPLE_WATCH("애플 워치 SE"),
    AIRPOT("에어팟 맥스"),
    MACBOOK("최신형 맥북");

    public final String item;

    public static RegretItem getRegretItemBymonthlyUsedWithRegret(BigDecimal monthlyUsedWithRegret) {
        if (monthlyUsedWithRegret.compareTo(BigDecimal.ZERO) > 0 && monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(5000)) < 0) {
            return COFFEE;
        } else if (monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(5000)) >= 0 && monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(10000)) < 0) {
            return HAMBURGER;
        } else if (monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(10000)) >= 0 && monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(50000)) < 0) {
            return CHICKEN;
        } else if (monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(50000)) >= 0 && monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(100000)) < 0) {
            return SUSHI;
        } else if (monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(100000)) >= 0 && monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(300000)) < 0) {
            return APPLE_PENCLE;
        } else if (monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(300000)) >= 0 && monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(500000)) < 0) {
            return APPLE_WATCH;
        } else if (monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(500000)) >= 0 && monthlyUsedWithRegret.compareTo(BigDecimal.valueOf(1000000)) < 0) {
            return AIRPOT;
        } else {
            return MACBOOK;
        }
    }

    public static String getImageURL() {
        return "ImageURL";
    }
}