package io.porko.domain.member.controller.model;

import com.querydsl.core.annotations.QueryProjection;
import io.porko.domain.member.domain.Member;
import java.time.LocalDateTime;

public record MemberResponse(
    Long id,
    String name,
    String email,
    String profileImageUrl,
    LocalDateTime registeredAt
) {
    @QueryProjection
    public MemberResponse {
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getName(),
            member.getEmail(),
            member.getProfileImageUrl(),
            member.getCreatedAt()
        );
    }
}
