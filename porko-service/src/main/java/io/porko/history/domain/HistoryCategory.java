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
public class HistoryCategory {

    private BigInteger imageUrlTypeNo;
    private String type; // 수입인지 지출인지를 나타냄.
    private String name;

    public static HistoryCategory of(BigInteger imageUrlTypeNo, String type, String name) {
        return new HistoryCategory(imageUrlTypeNo, type, name);
    }
}
