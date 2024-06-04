package io.porko.history.repo;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static io.porko.history.domain.QHistory.history;

@Repository
@RequiredArgsConstructor
public class HistoryQueryRepo {
    private final JPQLQueryFactory queryFactory;

    public Optional<BigDecimal> calcTotalCost (Integer goalYear, Integer goalMonth, Long memberId) {
        return Optional.ofNullable(queryFactory.select(history.cost.sum())
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.year().eq(goalYear))
                        .and(history.usedAt.month().eq(goalMonth))
                        .and(history.usedAt.dayOfMonth().lt(LocalDate.now().getDayOfMonth())))
                .fetchOne());
    }

    public Long countOverSpend(Integer goalYear, Integer goalMonth, Long memberId, BigDecimal dailyCost) {
        return Long.valueOf(queryFactory.select()
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.year().eq(goalYear))
                        .and(history.usedAt.month().eq(goalMonth))
                        .and(history.usedAt.dayOfMonth().lt(LocalDate.now().getDayOfMonth()))
                        .and(history.cost.gt(dailyCost)))
                .fetchCount());
    }

    public long countSpendingDate(Integer goalYear, Integer goalMonth, Long memberId) {
        return queryFactory.select(history.usedAt.dayOfMonth().count())
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.year().eq(goalYear))
                        .and(history.usedAt.month().eq(goalMonth))
                        .and(history.usedAt.dayOfMonth().lt(LocalDate.now().getDayOfMonth())))
                .fetchCount();
    }

    public Optional<BigDecimal> calcUsedCostInLastMonth (Integer currentYear, Integer currentMonth, Long memberId) {
        if (currentMonth == 1) {
            currentYear -= 1;
            currentMonth = 12;
        } else {
            currentMonth -= 1;
        }

        return Optional.ofNullable(queryFactory.select(history.cost.sum())
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.year().eq(currentYear))
                        .and(history.usedAt.month().eq(currentMonth)))
                .fetchOne());
    }
}
