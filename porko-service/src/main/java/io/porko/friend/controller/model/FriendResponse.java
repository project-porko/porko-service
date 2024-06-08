package io.porko.friend.controller.model;

import com.querydsl.core.annotations.QueryProjection;

public record FriendResponse(
        Long memberId,
        String name,
        String profileImageUrl
) {
    public static FriendResponse of (
            Long memberId,
            String name,
            String profileImageUrl) {
        return new FriendResponse(
                memberId,
                name,
                profileImageUrl);
    }
}
