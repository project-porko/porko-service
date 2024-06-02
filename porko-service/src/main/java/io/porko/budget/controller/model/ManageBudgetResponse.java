package io.porko.budget.controller.model;

import java.math.BigDecimal;

public record ManageBudgetResponse(
        BigDecimal goalCost,
        BigDecimal dailyCost,
        Long overSpend,
        Long zeroSpend

) {
    public static ManageBudgetResponse of(
            BigDecimal goalCost,
            BigDecimal dailyCost,
            Long overSpend,
            Long zeroSpend
    ) {
        return new ManageBudgetResponse(goalCost, dailyCost, overSpend, zeroSpend);
    }
}
