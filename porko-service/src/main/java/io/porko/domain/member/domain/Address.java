package io.porko.domain.member.domain;

import jakarta.persistence.Column;
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
public class Address {
    @Column(nullable = false)
    private String roadName;
    
    @Column(nullable = false, length = 30)
    private String detail;

    public static Address of(String roadName, String detail) {
        return new Address(roadName, detail);
    }
}
