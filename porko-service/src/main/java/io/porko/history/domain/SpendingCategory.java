package io.porko.history.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;

@Getter
@Embeddable
@AccessType(Type.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpendingCategory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String url;

    private SpendingCategory (String name, String url) {
        this.name = name;
        this.url = url;
    }
    public static SpendingCategory of(String name, String url) {
        return new SpendingCategory(name, url);
    }
}