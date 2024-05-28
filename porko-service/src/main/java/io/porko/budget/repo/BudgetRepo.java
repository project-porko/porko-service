package io.porko.budget.repo;

import io.porko.budget.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepo extends JpaRepository<Budget, Long> {
    Optional<Budget> findByGoalYearAndGoalMonthAndMemberId(Integer goalYear, Integer goalMonth, Long memberId);
}
