package io.porko.friend.domain;

import io.porko.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {
    @EmbeddedId
    private FriendId id;

    @ManyToOne
    @MapsId("memberId")
    private Member member;

    @ManyToOne
    @MapsId("friendId")
    private Member friend;
}
