package io.porko.budget.service;

import io.porko.budget.controller.model.BudgetRequest;
import io.porko.budget.controller.model.BudgetResponse;
import io.porko.budget.controller.model.ManageBudgetResponse;
import io.porko.budget.domain.Budget;
import io.porko.budget.exception.BudgetErrorCode;
import io.porko.budget.exception.BudgetException;
import io.porko.budget.repo.BudgetRepo;
import io.porko.history.repo.HistoryQueryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepo budgetRepo;
    private final HistoryQueryRepo historyQueryRepo;

    public BudgetResponse getBudget(Integer goalYear, Integer goalMonth, Long memberId) {
        Budget budget = budgetRepo.findByGoalYearAndGoalMonthAndMemberId(goalYear, goalMonth, memberId).orElseThrow(() -> new BudgetException(BudgetErrorCode.BUDGET_NOT_SET, goalYear, goalMonth, memberId));

        BigDecimal totalCost = historyQueryRepo.calcTotalCost(goalYear, goalMonth, memberId).orElse(BigDecimal.ZERO);

        return BudgetResponse.of(totalCost.divide(budget.getGoalCost())
                            .multiply(BigDecimal.valueOf(100))
                            .abs()
                            .setScale(1, RoundingMode.DOWN)
                            .stripTrailingZeros());
    }

    @Transactional
    public void setBudget (BudgetRequest budgetRequest, Long id) {
        Budget budget = budgetRequest.toEntity(id);

        budgetRepo.save(budget);
    }

    public ManageBudgetResponse manageBudget (Long memberId) {
        LocalDate today = LocalDate.now();

        Budget budget = budgetRepo.findByGoalYearAndGoalMonthAndMemberId(
                today.getYear(),
                today.getMonthValue(),
                memberId)
                .orElse(null);

        BigDecimal totalCost = historyQueryRepo.calcTotalCost(
                today.getYear(),
                today.getMonthValue(),
                memberId)
                .orElse(BigDecimal.ZERO);

        BigDecimal usableCost = budget.getGoalCost()
                .subtract(totalCost)
                .setScale(1, RoundingMode.DOWN)
                .stripTrailingZeros();

        int lastDayOfMonth = YearMonth.from(today).lengthOfMonth();

        BigDecimal dailyCost = usableCost.divide(BigDecimal.valueOf(lastDayOfMonth - today.getDayOfMonth()))
                .setScale(1, RoundingMode.DOWN)
                .stripTrailingZeros();

        return ManageBudgetResponse.of(
                usableCost,
                dailyCost,
                historyQueryRepo.countOverSpend(
                        today.getYear(),
                        today.getMonthValue(),
                        memberId,
                        dailyCost),
                historyQueryRepo.countSpendingDate(
                        today.getYear(),
                        today.getMonthValue(),
                        memberId
                )
        );
    }
}
