package io.porko.member.repo;

import io.porko.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {
}
