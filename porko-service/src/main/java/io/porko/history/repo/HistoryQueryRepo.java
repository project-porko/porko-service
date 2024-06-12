package io.porko.history.repo;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Optional;

import static io.porko.history.domain.QHistory.history;

@Repository
@RequiredArgsConstructor
public class HistoryQueryRepo {
    private final JPQLQueryFactory queryFactory;

    public Optional<BigDecimal> calcTotalCost(Integer goalYear, Integer goalMonth, Long memberId) {
        return Optional.ofNullable(queryFactory.select(history.cost.sum())
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.year().eq(goalYear))
                        .and(history.usedAt.month().eq(goalMonth))
                        .and(history.usedAt.dayOfMonth().lt(LocalDate.now().getDayOfMonth())))
                .fetchOne());
    }

    public Long countOverSpend(Integer goalYear, Integer goalMonth, Long memberId, BigDecimal dailyBudget) {
        return Long.valueOf(queryFactory.select()
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.year().eq(goalYear))
                        .and(history.usedAt.month().eq(goalMonth))
                        .and(history.usedAt.dayOfMonth().lt(LocalDate.now().getDayOfMonth()))
                        .and(history.cost.abs().gt(dailyBudget)))
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

    public Optional<BigDecimal> calcUsedCostInLastMonth(Integer currentYear, Integer currentMonth, Long memberId) {
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

    public Optional<BigDecimal> calcDailyUsedCost(LocalDate date, Long memberId) {
        return Optional.ofNullable(queryFactory.select(history.cost.sum())
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.year().eq(date.getYear()))
                        .and(history.usedAt.month().eq(date.getMonthValue()))
                        .and(history.usedAt.dayOfMonth().eq(date.getDayOfMonth())))
                .fetchOne());
    }

    public Optional<BigDecimal> calcDailyEarnedCost(LocalDate date, Long memberId) {
        return Optional.ofNullable(queryFactory.select(history.cost.sum())
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.gt(0))
                        .and(history.usedAt.year().eq(date.getYear()))
                        .and(history.usedAt.month().eq(date.getMonthValue()))
                        .and(history.usedAt.dayOfMonth().eq(date.getDayOfMonth())))
                .fetchOne());
    }

    public Optional<BigDecimal> calcUsedCostForPeriod(LocalDate startDate, LocalDate endDate, Long memberId) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);

        return Optional.ofNullable(queryFactory.select(history.cost.sum())
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.between(startDateTime, endDateTime)))
                .fetchOne());
    }

    public Optional<BigDecimal> calcEarnedCostForPeriod(LocalDate startDate, LocalDate endDate, Long memberId) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);

        return Optional.ofNullable(queryFactory.select(history.cost.sum())
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.gt(0))
                        .and(history.usedAt.between(startDateTime, endDateTime)))
                .fetchOne());
    }

    public Optional<BigDecimal> calcRegretCost(LocalDateTime startDateTime,
                                     LocalDateTime endDateTime,
                                     Long memberId) {
        return Optional.ofNullable(queryFactory.select(history.cost.sum())
                .from(history)
                .where(history.member.id.eq(memberId)
                        .and(history.cost.lt(0))
                        .and(history.usedAt.between(startDateTime, endDateTime))
                        .and(history.isRegret.isTrue()))
                        .fetchOne());
    }
}
