package io.porko.member.controller.model;

import com.querydsl.core.annotations.QueryProjection;
import io.porko.member.domain.Member;
import java.time.LocalDateTime;

public record MemberResponse(
    Long id,
    String memberId,
    String name,
    String email,
    LocalDateTime registeredAt
) {
    @QueryProjection
    public MemberResponse(Long id, String memberId, String name, String email, LocalDateTime registeredAt) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.registeredAt = registeredAt;
    }

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
