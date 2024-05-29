package io.porko.history.controller.model;

import com.querydsl.core.annotations.QueryProjection;
import io.porko.history.domain.History;
import io.porko.history.domain.SpendingCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoryResponse(
        String type,
        LocalDateTime usedAt,
        BigDecimal cost,
        String place,
        SpendingCategory spendingCategoryId,
        String payType
) {
    @QueryProjection
    public HistoryResponse{}

    public static HistoryResponse of(History history) {
        return new HistoryResponse(
                history.getType(),
                history.getUsedAt(),
                history.getCost(),
                history.getPlace(),
                history.getSpendingCategoryId(),
                history.getPayType()
        );
    }
}

