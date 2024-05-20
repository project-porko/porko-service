package io.porko.member.controller.model;

import io.porko.member.domain.Member;
import java.time.LocalDateTime;

public record MemberResponse(
    Long id,
    String memberId,
    String name,
    String email,
    LocalDateTime registeredAt
) {

    public static MemberResponse of(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getMemberId(),
            member.getName(),
            member.getEmail(),
            member.getCreatedAt()
        );
    }
}
