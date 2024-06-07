package io.porko.friend.controller;

import io.porko.auth.controller.model.LoginMember;
import io.porko.friend.controller.model.FriendResponse;
import io.porko.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<FriendResponse> getFriendId(@LoginMember Long id) {
        return ResponseEntity.ok(friendService.getFriendId(id));
    }
}