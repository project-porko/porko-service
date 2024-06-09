package io.porko.friend.controller.model;

import java.util.List;

public record FriendResponse(
    int friendCount,
    List<FriendList> friendList
) {
    public static FriendResponse of (int friendCount, List<FriendList> friendList) {
        return new FriendResponse(friendCount, friendList);
    }
}
