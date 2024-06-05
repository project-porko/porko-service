package io.porko.budget.controller.model;

import java.math.BigDecimal;

public record ManageBudgetResponse(
        BigDecimal goalCost,
        BigDecimal dailyCost,
        long overSpend,
        long zeroSpend

) {
    public static ManageBudgetResponse of(
            BigDecimal goalCost,
            BigDecimal dailyCost,
            long overSpend,
            long zeroSpend
    ) {
        return new ManageBudgetResponse(goalCost, dailyCost, overSpend, zeroSpend);
    }
}
