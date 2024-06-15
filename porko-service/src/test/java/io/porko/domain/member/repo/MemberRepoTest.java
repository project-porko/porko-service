package io.porko.domain.member.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.porko.config.base.persistence.JpaTestBase;
import io.porko.domain.member.domain.Address;
import io.porko.domain.member.domain.Gender;
import io.porko.domain.member.domain.Member;
import io.porko.domain.member.repo.MemberRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Repo:Member")
class MemberRepoTest extends JpaTestBase {
    private final MemberRepo memberRepo;

    public MemberRepoTest(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Test
    @DisplayName("회원 저장")
    void save() {
        // Given
        Member given = Member.of(
            "member@porko.info",
            "password",
            "name",
            "01012345678",
            Address.of("roadName", "detail"),
            Gender.MALE,
            "https://avatars.githubusercontent.com/u/169456863?s=200&v=4"
        );

        // When
        Member actual = memberRepo.save(given);

        // Then
        assertAll(
            () -> assertThat(actual.getId())
                .as("JPA PK 생성 전략에 의한 PK값 부여 여부 검증")
                .isNotNull(),
            () -> assertThat(actual.getCreatedAt())
                .as("JPA AuditingListener에 의한 생성일시 부여 여부 검증")
                .isNotNull(),
            () -> assertThat(actual.getUpdatedAt())
                .as("TestJpaConfiguration의 @EnableJpaAuditing(modifyOnCreate = false)설정에 의해 생성된 데이터의 수정일시 미 부여 여부 검증")
                .isNull()
        );
    }
}
