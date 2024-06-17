package io.porko.domain.history.controller.model;

import io.porko.domain.history.domain.History;
import io.porko.domain.history.domain.HistoryCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoryDetailResponse(
        LocalDateTime usedAt,
        BigDecimal cost,
        String place,
        HistoryCategory historyCategory,
        String payType,
        boolean isRegret,
        String memo
) {

    public static HistoryDetailResponse ofDetail(History history) {
        return new HistoryDetailResponse(
                history.getUsedAt(),
                history.getCost(),
                history.getPlace(),
                history.getHistoryCategory(),
                history.getPayType(),
                history.isRegret(),
                history.getMemo()
        );
    }
}
