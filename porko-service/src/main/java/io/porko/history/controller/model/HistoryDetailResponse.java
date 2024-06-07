package io.porko.history.controller.model;

import io.porko.history.domain.History;
import io.porko.history.domain.HistoryCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoryDetailResponse(
        LocalDateTime usedAt,
        BigDecimal cost,
        String place,
        HistoryCategory historyCategoryId,
        String payType,
        boolean isRegret,
        String memo
) {

    public static HistoryDetailResponse ofDetail(History history) {
        return new HistoryDetailResponse(
                history.getUsedAt(),
                history.getCost(),
                history.getPlace(),
                history.getHistoryCategoryId(),
                history.getPayType(),
                history.isRegret(),
                history.getMemo()
        );
    }
}
