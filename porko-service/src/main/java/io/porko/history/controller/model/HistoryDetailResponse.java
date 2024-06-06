package io.porko.history.controller.model;

import io.porko.history.domain.History;
import io.porko.history.domain.SpendingCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoryDetailResponse(
        LocalDateTime usedAt,
        BigDecimal cost,
        String place,
        SpendingCategory spendingCategoryId,
        String payType,
        Boolean regret,
        String memo
){

    public static HistoryDetailResponse ofDetail(History history) {
        return new HistoryDetailResponse(
                history.getUsedAt(),
                history.getCost(),
                history.getPlace(),
                history.getSpendingCategoryId(),
                history.getPayType(),
                history.getRegret(),
                history.getMemo()
        );
    }
}
