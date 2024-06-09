package io.porko.friend.service;

import io.porko.friend.controller.model.FriendListResponse;
import io.porko.friend.controller.model.FriendResponse;
import io.porko.friend.domain.Friend;
import io.porko.friend.repo.FriendRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepo friendRepo;

    public FriendResponse getFriendResponse(Long id) {
        List<FriendListResponse> list = getFriendList(id);

        return FriendResponse.of(list.size(), list);
    }

    public List<FriendListResponse> getFriendList(Long id) {
        List<FriendListResponse> list = new ArrayList<>();

        list.addAll(getFriendListByMemberId(id));
        list.addAll(getMemberListByFriendId(id));

        return list;
    }

    public List<FriendListResponse> getFriendListByMemberId(Long id) {
        List<Friend> friendList = friendRepo.findAllByIdMemberId(id);

        return friendList.stream()
                .map(friend -> FriendListResponse.of(
                        friend.getFriend().getId(),
                        friend.getFriend().getName(),
                        friend.getFriend().getProfileImageUrl()))
                .collect(Collectors.toList());
    }

    public List<FriendListResponse> getMemberListByFriendId(Long id) {
        List<Friend> memberList = friendRepo.findAllByIdFriendId(id);

        return memberList.stream()
                .map(friend -> FriendListResponse.of(
                        friend.getMember().getId(),
                        friend.getMember().getName(),
                        friend.getMember().getProfileImageUrl()))
                .collect(Collectors.toList());
    }
}
