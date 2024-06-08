package io.porko.friend.service;

import io.porko.friend.controller.model.FriendResponse;
import io.porko.friend.domain.Friend;
import io.porko.friend.repo.FriendRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepo friendRepo;
    public List<FriendResponse> getFriendList(Long id) {
        List<FriendResponse> list = new ArrayList<>();

        Optional<List<FriendResponse>> friendList = getFriendListByMemberId(id);
        Optional<List<FriendResponse>> memberList = getMemberListByFriendId(id);

        friendList.ifPresent(list::addAll);
        memberList.ifPresent(list::addAll);

        return list;
    }

    public Optional<List<FriendResponse>> getFriendListByMemberId(Long id) {
        Optional<List<Friend>> friendListOptional = friendRepo.findAllByIdMemberId(id);

        return friendListOptional.map(friendList ->
                friendList.stream()
                        .map(friend -> FriendResponse.of(
                                friend.getFriend().getId(),
                                friend.getFriend().getName(),
                                friend.getFriend().getProfileImageUrl()))
                        .collect(Collectors.toList()));
    }

    public Optional<List<FriendResponse>> getMemberListByFriendId(Long id) {
        Optional<List<Friend>> memberListOptional = friendRepo.findAllByIdFriendId(id);

        return memberListOptional.map(memberList ->
                memberList.stream()
                .map(friend -> FriendResponse.of(
                        friend.getMember().getId(),
                        friend.getMember().getName(),
                        friend.getMember().getProfileImageUrl()))
                .collect(Collectors.toList()));
    }
}
