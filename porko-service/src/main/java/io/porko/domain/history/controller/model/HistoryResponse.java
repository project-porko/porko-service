package io.porko.domain.history.controller.model;

import com.querydsl.core.annotations.QueryProjection;
import io.porko.domain.history.domain.History;
import io.porko.domain.history.domain.HistoryCategory;

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
