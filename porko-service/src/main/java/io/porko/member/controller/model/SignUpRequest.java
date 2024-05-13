package io.porko.member.controller.model;

import io.porko.member.domain.Gender;
import io.porko.member.domain.Member;

public record SignUpRequest(
    String memberId,
    String password,
    String name,
    String email,
    String phoneNumber,
    AddressDto address,
    Gender gender
) {
    public Member toEntity(String encodedPassword) {
        return Member.of(
            memberId,
            encodedPassword,
            name,
            email,
            phoneNumber,
            address.toEntity(),
            gender
        );
    }
}
