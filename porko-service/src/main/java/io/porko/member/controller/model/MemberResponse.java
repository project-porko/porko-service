package io.porko.member.controller.model;

import com.querydsl.core.annotations.QueryProjection;
import io.porko.member.domain.Member;
import java.time.LocalDateTime;

public record MemberResponse(
    Long id,
    String name,
    String email,
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
            member.getCreatedAt()
        );
    }
}
