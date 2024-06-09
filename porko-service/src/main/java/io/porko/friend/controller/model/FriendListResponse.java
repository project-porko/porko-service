package io.porko.friend.controller.model;

public record FriendListResponse(
        Long memberId,
        String name,
        String profileImageUrl
) {
    public static FriendListResponse of (
            Long memberId,
            String name,
            String profileImageUrl) {
        return new FriendListResponse(
                memberId,
                name,
                profileImageUrl);
    }
}
