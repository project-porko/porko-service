package io.porko.history.repo;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

import static io.porko.history.domain.QHistory.history;

@Repository
@RequiredArgsConstructor
public class HistoryQueryRepo {
    private final JPQLQueryFactory queryFactory;

    public Optional<BigDecimal> calcTotalCost (Integer goalYear, Integer goalMonth, Long memberId) {
        return Optional.ofNullable(queryFactory.select(history.cost.sum())
                .from(history)
                .where(history.memberId.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.year().eq(goalYear))
                        .and(history.usedAt.month().eq(goalMonth))
                        .and(history.usedAt.dayOfMonth().lt(LocalDate.now().getDayOfMonth())))
                .fetchOne());
    }
}
