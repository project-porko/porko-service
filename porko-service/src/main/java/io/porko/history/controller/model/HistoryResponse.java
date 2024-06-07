package io.porko.history.controller.model;

import com.querydsl.core.annotations.QueryProjection;
import io.porko.history.domain.History;
import io.porko.history.domain.HistoryCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoryResponse(
        Long id,
        LocalDateTime usedAt,
        BigDecimal cost,
        String place,
        HistoryCategory historyCategoryId,
        String payType,
        boolean isRegret

) {
    @QueryProjection
    public HistoryResponse {
    }

    public static HistoryResponse of(History history) {
        return new HistoryResponse(
                history.getId(),
                history.getUsedAt(),
                history.getCost(),
                history.getPlace(),
                history.getHistoryCategoryId(),
                history.getPayType(),
                history.isRegret()
        );
    }
}
