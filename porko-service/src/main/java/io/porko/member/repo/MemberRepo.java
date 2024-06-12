package io.porko.member.repo;

import io.porko.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhoneNumber(String phoneNumber);
}
