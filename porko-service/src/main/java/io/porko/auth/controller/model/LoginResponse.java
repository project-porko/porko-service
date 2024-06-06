package io.porko.auth.controller.model;

import io.porko.member.domain.Member;
import java.time.LocalDateTime;

public record LoginResponse(
    Long id,
    String name,
    String email,
    String accessToken,
    String profileImageUrl,
    LocalDateTime loggedInAt
) {
    public static LoginResponse of(Member member, String accessToken) {
        return new LoginResponse(
            member.getId(),
            member.getName(),
            member.getEmail(),
            accessToken,
            member.getProfileImageUrl(),
            LocalDateTime.now()
        );
    }
}
