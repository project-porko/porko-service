package io.porko.history.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;

@Getter
@Embeddable
@AccessType(Type.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryCategory {
    private int imageUrlTypeNo;
    private String type;
    private String name;

    public static HistoryCategory of(int imageUrlTypeNo, String type, String name) {
        return new HistoryCategory(imageUrlTypeNo, type, name);
    }
}
