package io.porko.budget.controller.model;

import io.porko.budget.domain.Budget;
import io.porko.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetRequest(
        @NotBlank
        @Size(min = 5, max = 9)
        BigDecimal cost
) {
    public Budget toEntity(Member member) {
        return Budget.of(
                member,
                cost,
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue()
        );
    }
}
