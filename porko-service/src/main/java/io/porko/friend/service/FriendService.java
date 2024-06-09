package io.porko.friend.service;

import io.porko.friend.controller.model.FriendList;
import io.porko.friend.controller.model.FriendResponse;
import io.porko.friend.domain.Friend;
import io.porko.friend.repo.FriendRepo;
import io.porko.member.domain.Member;
import io.porko.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepo friendRepo;
    private final MemberService memberService;
    public FriendResponse getFriendResponse(Long id) {
        List<FriendList> list = new ArrayList<>();

        list.add(getCurrentMember(id));
        list.addAll(getFriendList(id));

        return FriendResponse.of(list.size(), list);
    }

    public List<FriendList> getFriendList(Long id) {
        List<FriendList> list = new ArrayList<>();

        list.addAll(getFriendListByMemberId(id));
        list.addAll(getMemberListByFriendId(id));

        return list;
    }

    public List<FriendList> getFriendListByMemberId(Long id) {
        List<Friend> friendList = friendRepo.findAllByIdMemberId(id);

        return friendList.stream()
                .map(friend -> FriendList.of(
                        friend.getFriend().getId(),
                        friend.getFriend().getName(),
                        friend.getFriend().getProfileImageUrl()))
                .collect(Collectors.toList());
    }

    public List<FriendList> getMemberListByFriendId(Long id) {
        List<Friend> memberList = friendRepo.findAllByIdFriendId(id);

        return memberList.stream()
                .map(friend -> FriendList.of(
                        friend.getMember().getId(),
                        friend.getMember().getName(),
                        friend.getMember().getProfileImageUrl()))
                .collect(Collectors.toList());
    }

    public FriendList getCurrentMember(Long currentMemberId) {
        Member member = memberService.findMemberById(currentMemberId);

        return FriendList.of(
                member.getId(),
                member.getName(),
                member.getProfileImageUrl());
    }
}