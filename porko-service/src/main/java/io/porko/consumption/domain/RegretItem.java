package io.porko.consumption.domain;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;

@AllArgsConstructor
@RequiredArgsConstructor
public enum RegretItem {
    ZERO ("ZERO", 0, 1) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, cost);
        }
    },
    COFFEE ("커피 한 잔", 0, 5000, 2) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    HAMBURGER("햄버거 세트", 5000, 10000, 3) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    CHICKEN("치킨 두 마리", 10000, 50000, 4) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    SUSHI("스시 오마카세", 50000, 100000, 5) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    APPLE_PENCLE("애플 펜슬", 100000, 300000, 6) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    APPLE_WATCH("애플 워치 SE", 300000, 500000, 7) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    AIRPODS("에어팟 맥스", 500000, 1000000, 8) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, endRange, cost);
        }
    },
    MACBOOK("최신형 맥북", 1000000, 9) {
        @Override
        boolean isMatched(BigDecimal cost) {
            return isRange(startRange, cost);
        }
    };

    public final String item;
    final int startRange;
    int endRange;
    final int itemId;

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
        if (start == 0) {
            return cost.compareTo(BigDecimal.valueOf(start)) == 0;
        }
        return cost.compareTo(BigDecimal.valueOf(start)) >= 0;
    }

    public static RegretItem getRegretItemBymonthlyUsedWithRegret(BigDecimal monthlyUsedWithRegret) {
        return Arrays.stream(values())
                .filter(it -> it.isMatched(monthlyUsedWithRegret))
                .findFirst()
                .orElse(MACBOOK);
    }
}