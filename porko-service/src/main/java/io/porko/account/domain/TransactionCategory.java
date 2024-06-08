package io.porko.account.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Access(AccessType.FIELD)
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionCategory {
    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String detail;

    public static TransactionCategory of(String category, String categoryDetail) {
        return new TransactionCategory(category, categoryDetail);
    }
}
