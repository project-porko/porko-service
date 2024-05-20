package io.porko.member.repo;

import io.porko.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByMemberId(String memberId);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Member> findByMemberId(String memberId);
}
