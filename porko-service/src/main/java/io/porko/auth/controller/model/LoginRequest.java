package io.porko.auth.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank
    @Size(min = 6, max = 20)
    String memberId,

    @NotBlank
    String password
) {
}
