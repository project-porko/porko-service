package io.porko.domain.friend.domain;

import io.porko.domain.member.domain.Member;
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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @MapsId("friendId")
    @JoinColumn(name = "friend_id", nullable = false)
    private Member friend;

    private Friend (Member member, Member friend){
        this.id = new FriendId(member.getId(), friend.getId());
        this.member = member;
        this.friend = friend;
    }

    public static Friend of(Member member, Member friend) {
        return new Friend(member, friend);
    }
}
