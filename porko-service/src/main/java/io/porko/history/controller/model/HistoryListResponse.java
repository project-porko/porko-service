package io.porko.history.controller.model;

import java.math.BigDecimal;
import java.util.List;

public record HistoryListResponse(
        BigDecimal totalSpent,
        BigDecimal totalEarned,
        List<HistoryResponse> historyList
) {

    public static HistoryListResponse of(List<HistoryResponse> historyResponses, BigDecimal totalSpent, BigDecimal totalEarned) {
        return new HistoryListResponse(
                totalSpent,
                totalEarned,
                historyResponses);
    }
}
