package io.porko.member.controller.model;

import io.porko.member.domain.Gender;
import io.porko.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
    @NotBlank
    @Size(min = 6, max = 20)
    String memberId,

    @NotBlank
    String password,

    @NotBlank
    @Size(max = 10)
    String name,

    @NotBlank
    @Size(max = 40)
    @Email
    String email,

    @NotBlank
    @Size(max = 11)
    String phoneNumber,

    AddressDto address,

    @NotNull
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
