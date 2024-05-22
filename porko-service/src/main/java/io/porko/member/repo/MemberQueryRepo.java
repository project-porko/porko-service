package io.porko.member.repo;

import static io.porko.member.domain.QMember.member;

import com.querydsl.jpa.JPQLQueryFactory;
import io.porko.member.controller.model.MemberResponse;
import io.porko.member.controller.model.QMemberResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepo {
    private final JPQLQueryFactory queryFactory;

    public Optional<MemberResponse> findMemberById(Long id) {
        return Optional.ofNullable(queryFactory.select(
                new QMemberResponse(
                    member.id,
                    member.name,
                    member.email,
                    member.createdAt
                ))
            .from(member)
            .where(member.id.eq(id))
            .fetchOne());
    }
}
