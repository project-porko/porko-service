package io.porko.domain.friend.controller.model;

public record FriendList(
        Long memberId,
        String name,
        String profileImageUrl
) {
    public static FriendList of (
            Long memberId,
            String name,
            String profileImageUrl) {
        return new FriendList(
                memberId,
                name,
                profileImageUrl);
    }
}
