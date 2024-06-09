package io.porko.budget.service;

import io.porko.budget.controller.model.BudgetRequest;
import io.porko.budget.controller.model.BudgetResponse;
import io.porko.budget.controller.model.ManageBudgetResponse;
import io.porko.budget.domain.Budget;
import io.porko.budget.exception.BudgetErrorCode;
import io.porko.budget.exception.BudgetException;
import io.porko.budget.repo.BudgetRepo;
import io.porko.history.repo.HistoryQueryRepo;
import io.porko.member.exception.MemberErrorCode;
import io.porko.member.exception.MemberException;
import io.porko.member.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepo budgetRepo;
    private final MemberRepo memberRepo;
    private final HistoryQueryRepo historyQueryRepo;

    public BudgetResponse getBudget(Integer goalYear, Integer goalMonth, Long memberId) {
        return BudgetResponse.of(calcUsedBudget(goalYear, goalMonth, memberId));
    }

    @Transactional
    public void setBudget (BudgetRequest budgetRequest, Long id) {
        Budget budget = budgetRequest.toEntity(memberRepo.findById(id).orElseThrow(()-> new MemberException(MemberErrorCode.NOT_FOUND)));

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
                .add(totalCost)
                .setScale(1, RoundingMode.DOWN)
                .stripTrailingZeros();

        int lastDayOfMonth = YearMonth.from(today).lengthOfMonth();

        BigDecimal dailyCost = usableCost.divide(BigDecimal.valueOf(lastDayOfMonth - today.getDayOfMonth() + 1), 0, RoundingMode.DOWN)
                .stripTrailingZeros();

        return ManageBudgetResponse.of(
                usableCost,
                dailyCost,
                historyQueryRepo.countOverSpend(
                        today.getYear(),
                        today.getMonthValue(),
                        memberId,
                        dailyCost),
                today.getDayOfMonth() - historyQueryRepo.countSpendingDate(
                        today.getYear(),
                        today.getMonthValue(),
                        memberId
                )
        );
    }

    public BudgetResponse getUsedCostInLastMonth(Long memberId) {
        return BudgetResponse.of(historyQueryRepo.calcUsedCostInLastMonth(
                        LocalDate.now().getYear(),
                        LocalDate.now().getMonthValue(),
                        memberId)
                .orElse(BigDecimal.ZERO)
                .abs()
                .stripTrailingZeros());
    }

    public BigDecimal calcDailyBudget(LocalDate date, Long memberId) {
        Optional<Budget> budget = budgetRepo.findByGoalYearAndGoalMonthAndMemberId(
                date.getYear(),
                date.getMonthValue(),
                memberId
        );

        return budget.map(value -> value.getGoalCost()
                .divide(
                        BigDecimal.valueOf(YearMonth.from(date).lengthOfMonth()),
                        0,
                        RoundingMode.DOWN
                )
                .stripTrailingZeros()
        ).orElse(null);
    }

    public BigDecimal calcUsedBudget (Integer goalYear, Integer goalMonth, Long memberId) {
        Budget budget = budgetRepo.findByGoalYearAndGoalMonthAndMemberId(goalYear, goalMonth, memberId).orElseThrow(() -> new BudgetException(BudgetErrorCode.BUDGET_NOT_SET, goalYear, goalMonth, memberId));

        BigDecimal totalCost = historyQueryRepo.calcTotalCost(goalYear, goalMonth, memberId).orElse(BigDecimal.ZERO);

        return totalCost.divide(budget.getGoalCost())
                .multiply(BigDecimal.valueOf(100))
                .abs()
                .setScale(1, RoundingMode.DOWN)
                .stripTrailingZeros();
    }
}
