package io.porko.domain.friend.repo;

import io.porko.domain.friend.domain.Friend;
import io.porko.domain.friend.domain.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepo extends JpaRepository<Friend, FriendId> {
    List<Friend> findAllByIdMemberId(Long id);
    List<Friend> findAllByIdFriendId(Long id);
}
