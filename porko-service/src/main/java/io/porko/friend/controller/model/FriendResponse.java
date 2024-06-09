package io.porko.friend.controller.model;

import java.util.List;

public record FriendResponse(
    int friendCount,
    List<FriendListResponse> friendListResponse
) {
    public static FriendResponse of (int friendCount, List<FriendListResponse> friendListResponse) {
        return new FriendResponse(friendCount, friendListResponse);
    }
}
