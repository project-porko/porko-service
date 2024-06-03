package io.porko.history.controller.model;

import com.querydsl.core.annotations.QueryProjection;
import io.porko.history.domain.History;
import io.porko.history.domain.SpendingCategory;
import io.porko.history.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoryResponse(
        TransactionType type,
        LocalDateTime usedAt,
        BigDecimal cost,
        String place,
        SpendingCategory spendingCategoryId,
        String payType,
        BigDecimal totalSpent,
        BigDecimal totalEarned,
        Boolean regret,
        String memo
) {
    @QueryProjection
    public HistoryResponse{}

    public static HistoryResponse of(History history,BigDecimal totalSpent, BigDecimal totalEarned) {
        return new HistoryResponse(
                history.getType(),
                history.getUsedAt(),
                history.getCost(),
                history.getPlace(),
                history.getSpendingCategoryId(),
                history.getPayType(),
                totalSpent,
                totalEarned,
                history.getRegret(),
                null
        );
    }

    public static  HistoryResponse ofDetail (History history) {
        return new HistoryResponse(
                null,
                history.getUsedAt(),
                history.getCost(),
                history.getPlace(),
                history.getSpendingCategoryId(),
                history.getPayType(),
                null, // totalSpent
                null, // totalEarned
                history.getRegret(),
                history.getMemo()
        );
    }
}
