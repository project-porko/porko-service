package io.porko.budget.repo;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BudgetQueryRepo {
    private final JPQLQueryFactory queryFactory;
}
