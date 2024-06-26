package io.porko.domain.friend.controller;

import io.porko.domain.auth.controller.model.LoginMember;
import io.porko.domain.consumption.controller.model.FriendRequest;
import io.porko.domain.friend.controller.model.FriendResponse;
import io.porko.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<FriendResponse> getFriendResponse(@LoginMember Long id) {
        return ResponseEntity.ok(friendService.getFriendResponse(id));
    }

    @PostMapping
    public ResponseEntity<Void> addFriend(@RequestBody FriendRequest friendRequest, @LoginMember Long id) {
        friendService.addFriend(friendRequest.phoneNumber(), id);

        return ResponseEntity.ok().build();
    }
}
