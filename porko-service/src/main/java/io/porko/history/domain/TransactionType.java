package io.porko.history.domain;

public enum TransactionType {
    SPENT("지출"),
    EARNED("수입");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }
}
