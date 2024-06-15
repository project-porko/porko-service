package io.porko.domain.history.controller.model;

import io.porko.domain.history.domain.History;
import io.porko.pagination.response.PageResponse;
import java.math.BigDecimal;

public record HistoryListResponse<T, E>(
    BigDecimal totalSpent,
    BigDecimal totalEarned,
    PageResponse<T, E> historyList
) {

    public static HistoryListResponse<History, HistoryResponse> of(
        BigDecimal totalSpent,
        BigDecimal totalEarned,
        PageResponse<History, HistoryResponse> pageResponse
    ) {
        return new HistoryListResponse<>(
            totalSpent,
            totalEarned,
            pageResponse
        );
    }
}
