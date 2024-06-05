package io.porko.member.repo;

import static org.assertj.core.api.Assertions.assertThat;

import io.porko.config.querydsl.QueryDslTestBase;
import io.porko.member.controller.model.MemberResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Repo:Query:Member")
class MemberQueryRepoTest extends QueryDslTestBase {
    private final MemberQueryRepo memberQueryRepo;

    public MemberQueryRepoTest(MemberQueryRepo memberQueryRepo) {
        this.memberQueryRepo = memberQueryRepo;
    }

    @Test
    @DisplayName("회원 조회")
    void findMemberById() {
        // When
        MemberResponse actual = Assertions.assertDoesNotThrow(() -> memberQueryRepo.findMemberById(1L).orElseThrow());

        // Then
        assertThat(actual).isNotNull();
    }
}
