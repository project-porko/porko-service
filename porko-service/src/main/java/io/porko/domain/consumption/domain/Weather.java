package io.porko.domain.consumption.domain;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;

@AllArgsConstructor
@RequiredArgsConstructor
public enum Weather {
    RAINBOW("무지출이 뜬 날", 0, 1) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, cost);
        }
    },
    SUN("지출 하늘이 맑은 날", 0, 33, 2) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    CLOUD("지출 구름이 낀 날", 33, 66, 3) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    RAIN("지출 비가 내리는 날", 66, 100, 4) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    THUNDER("지출 번개가 치는 날", 100, 5) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, cost);
        }
    };

    public final String weatherName;
    final int startRange;
    int endRange;
    public final Integer weatherImageNo;

    abstract boolean isMatched(BigDecimal cost);

    public boolean isRange(int start, int end, BigDecimal cost) {
        BigDecimal startRange = BigDecimal.valueOf(start);
        BigDecimal endRange = BigDecimal.valueOf(end);

        if (start == 0) {
            return cost.compareTo(startRange) > 0 && cost.compareTo(endRange) < 0;
        }

        return cost.compareTo(startRange) >= 0 && cost.compareTo(endRange) < 0;
    }

    public boolean isRange(int start, BigDecimal cost) {
        return cost.compareTo(BigDecimal.valueOf(start)) == 0;
    }

    public static Weather getWeatherByDailyUsed(BigDecimal dailyUsed) {
        return Arrays.stream(values())
                .filter(it -> it.isMatched(dailyUsed))
                .findFirst()
                .orElse(THUNDER);
    }
}
