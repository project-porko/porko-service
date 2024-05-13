package io.porko.member.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.porko.config.base.persistence.JpaTestBase;
import io.porko.member.domain.Address;
import io.porko.member.domain.Gender;
import io.porko.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Domain:Member")
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
            "memberId",
            "password",
            "name",
            "email",
            "01012345678",
            Address.of("roadName", "detail"),
            Gender.MALE
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
