package io.porko.member.controller.model.signup;

import io.porko.member.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDto(
    @NotBlank
    String roadName,

    @NotBlank
    @Size(max = 30)
    String detail
) {
    public Address toEntity() {
        return Address.of(roadName, detail);
    }
}
