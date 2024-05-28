package io.porko.budget.service;

import io.porko.budget.controller.model.BudgetResponse;
import io.porko.budget.domain.Budget;
import io.porko.budget.exception.BudgetErrorCode;
import io.porko.budget.exception.BudgetException;
import io.porko.budget.repo.BudgetRepo;
import io.porko.history.repo.HistoryQueryRepo;
import io.porko.utils.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
}
