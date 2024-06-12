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
        Budget budget = budgetRepo.findByGoalYearAndGoalMonthAndMemberId(goalYear, goalMonth, memberId).orElseThrow(() -> new BudgetException(BudgetErrorCode.BUDGET_NOT_SET, goalYear, goalMonth, memberId));

        YearMonth yearMonth = YearMonth.of(goalYear, goalMonth);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        if (yearMonth.getYear() == LocalDate.now().getYear()
                && yearMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            endDate = LocalDate.now();
        }

        BigDecimal totalCost = historyQueryRepo.calcUsedCostForPeriod(startDate, endDate, memberId).orElse(BigDecimal.ZERO);

        return BudgetResponse.of(totalCost.divide(budget.getGoalCost(), 3, RoundingMode.DOWN)
                            .multiply(BigDecimal.valueOf(100))
                            .abs()
                            .setScale(1, RoundingMode.DOWN)
                            .stripTrailingZeros());
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
                        budget.getGoalCost().divide(BigDecimal.valueOf(lastDayOfMonth), 0, RoundingMode.DOWN)),
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

    @Transactional
    public void updateBudget (BudgetRequest budgetRequest, Long id) {
        Budget budget = budgetRepo.findByGoalYearAndGoalMonthAndMemberId(
                        LocalDate.now().getYear(),
                        LocalDate.now().getMonthValue(),
                        id)
                .orElseThrow(() -> new BudgetException(BudgetErrorCode.BUDGET_NOT_SET,
                        LocalDate.now().getYear(),
                        LocalDate.now().getMonthValue(),
                        id));

        budget.setGoalCost(budgetRequest.cost());

        budgetRepo.save(budget);
    }
}
