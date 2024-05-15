package io.porko.member.controller.model.signup;

import io.porko.member.domain.Address;

public record AddressDto(
    String roadName,
    String detail
) {
    public Address toEntity() {
        return Address.of(roadName, detail);
    }
}
