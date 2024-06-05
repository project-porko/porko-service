package io.porko.auth.controller.model;

import java.time.LocalDateTime;

public record LoginResponse(
    Long id,
    String email,
    String accessToken,
    LocalDateTime loggedInAt
) {
    public static LoginResponse of(PorkoPrincipal porkoPrincipal, String accessToken) {
        return new LoginResponse(porkoPrincipal.getId(), porkoPrincipal.getUsername(), accessToken, LocalDateTime.now());
    }
}
