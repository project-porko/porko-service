package io.porko.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {
    private String roadName;
    private String detail;

    public static Address of(String roadName, String detail) {
        return new Address(roadName, detail);
    }
}
