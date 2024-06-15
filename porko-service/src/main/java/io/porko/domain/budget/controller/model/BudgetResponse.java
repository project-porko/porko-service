package io.porko.domain.budget.controller.model;

import com.querydsl.core.annotations.QueryProjection;

import java.math.BigDecimal;

public record BudgetResponse(
        BigDecimal used
) {
    @QueryProjection
    public BudgetResponse (BigDecimal used) {
        this.used = used;
    }

    public static BudgetResponse of(BigDecimal used) {
        return new BudgetResponse(used);
    }
}
