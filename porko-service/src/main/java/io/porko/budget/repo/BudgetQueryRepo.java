package io.porko.budget.repo;

import com.querydsl.jpa.JPQLQueryFactory;
import io.porko.budget.controller.model.BudgetResponse;
import io.porko.budget.controller.model.QBudgetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static io.porko.budget.domain.QBudget.budget;

@Repository
@RequiredArgsConstructor
public class BudgetQueryRepo {
    private final JPQLQueryFactory queryFactory;

    public Optional<BudgetResponse> findThisMonthBudgetById(Long id) {
        return Optional.ofNullable(queryFactory.select(
                new QBudgetResponse(
                    budget.goalCost
                ))
            .from(budget)
            .where(budget.memberId.eq(id))
            .fetchOne());
    }
}
