package io.porko.consumption.controller.model;

import java.math.BigDecimal;

public record RegretResponse(
    int regretItemImageNo,
    BigDecimal regretCost
) {
    public static RegretResponse of (int regretItemImageNo, BigDecimal regretCost) {
        return new RegretResponse(regretItemImageNo, regretCost);
    }
}
