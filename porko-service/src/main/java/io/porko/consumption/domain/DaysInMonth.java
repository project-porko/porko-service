package io.porko.consumption.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DaysInMonth {
    JANUARY(31),
    FEBRUARY(28),
    MARCH(31),
    APRIL(30),
    MAY(31),
    JUNE(30),
    JULY(31),
    AUGUST(31),
    SEPTEMBER(30),
    OCTOBER(31),
    NOVEMBER(30),
    DECEMBER(31);

    private final int days;

    public int getDays(Integer year) {
        if (this == FEBRUARY && year/4 == 0) {
            return 29;
        }
        return days;
    }
}
