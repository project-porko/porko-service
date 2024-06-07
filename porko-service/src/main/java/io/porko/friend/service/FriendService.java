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
    public FriendResponse getFriendId(Long id) {
        List<Long> list = new ArrayList<>();

        list.addAll(getFriendIdByMemberId(id));
        list.addAll(getMemberIdByFriendId(id));

        return FriendResponse.of(list);
    }

    private List<Long> getFriendIdByMemberId(Long id) {
        Optional<List<Friend>> list = friendRepo.findAllByIdMemberId(id);

        return list.orElse(List.of())
                .stream()
                .map(friend -> friend.getId().getFriendId())
                .collect(Collectors.toList());
    }

    private List<Long> getMemberIdByFriendId(Long id) {
        Optional<List<Friend>> list = friendRepo.findAllByIdFriendId(id);

        return list.orElse(List.of())
                .stream()
                .map(friend -> friend.getId().getMemberId())
                .collect(Collectors.toList());
    }
}
