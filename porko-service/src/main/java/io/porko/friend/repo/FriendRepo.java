package io.porko.friend.repo;

import io.porko.friend.domain.Friend;
import io.porko.friend.domain.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepo extends JpaRepository<Friend, FriendId> {
    public Optional<List<Friend>> findAllByIdMemberId(Long id);
    public Optional<List<Friend>> findAllByIdFriendId(Long id);
}
