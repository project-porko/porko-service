package io.porko.domain.member.controller.model.validateduplicate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ValidateDuplicateRequest(
    @NotNull
    ValidateDuplicateType type,

    @NotBlank
    String value
) {
    public static ValidateDuplicateRequest of(ValidateDuplicateType requestType, String value) {
        requestType.validateFormat(value);
        return new ValidateDuplicateRequest(requestType, value);
    }
}
