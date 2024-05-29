package io.porko.history.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;

import java.math.BigInteger;

@Getter
@Embeddable
@AccessType(Type.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpendingCategory {

    private BigInteger categoryId;
    private String name;
    private String url;

    public static SpendingCategory of(BigInteger categoryId, String name, String url) {
        return new SpendingCategory(categoryId, name, url);
    }
}
