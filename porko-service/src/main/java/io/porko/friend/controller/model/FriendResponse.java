package io.porko.friend.controller.model;

import com.querydsl.core.annotations.QueryProjection;

import java.util.List;

public record FriendResponse(
        List<Long> friendId
) {
    @QueryProjection
    public FriendResponse (List<Long> friendId) {this.friendId = friendId;}
}
