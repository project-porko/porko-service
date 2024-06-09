package io.porko.account.domain;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    SPENT("지출"), EARNED("수입"), TRANSFER("이체");

    private final String description;

    public static TransactionType valueOfName(String transactionType) {
        return Stream.of(values())
            .filter(it -> isEqualsTypeName(it.description, transactionType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(transactionType));
    }

    private static boolean isEqualsTypeName(String description, String transactionType) {
        return description.equals(transactionType);
    }

    private boolean isSpend() {
        return this == SPENT;
    }

    public boolean isNotSpend() {
        return !isSpend();
    }
}
