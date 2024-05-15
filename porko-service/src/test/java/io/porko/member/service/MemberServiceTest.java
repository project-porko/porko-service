package io.porko.member.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import io.porko.config.base.business.ServiceTestBase;
import io.porko.config.fixture.FixtureCommon;
import io.porko.member.controller.model.SignUpRequest;
import io.porko.member.domain.Member;
import io.porko.member.exception.MemberErrorCode;
import io.porko.member.exception.MemberException;
import io.porko.member.repo.MemberRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("Service:Member")
class MemberServiceTest extends ServiceTestBase {
    @Mock
    private MemberRepo memberRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    private final SignUpRequest 회원_가입_요청_객체 = FixtureCommon.dtoType().giveMeOne(SignUpRequest.class);

    @Test
    @DisplayName("회원 생성")
    void createMember() {
        // Given
        회원_생성_의존성_행동_정의(회원_가입_요청_객체);

        // When
        회원_생성(회원_가입_요청_객체);

        // Then
        회원_생성_의존성_동작_검증(회원_가입_요청_객체);
    }

    private void 회원_생성_의존성_행동_정의(SignUpRequest signUpRequest) {
        given(memberRepo.existsByMemberId(signUpRequest.memberId())).willReturn(false);
        given(memberRepo.existsByEmail(signUpRequest.email())).willReturn(false);
        given(memberRepo.save(any(Member.class))).will(AdditionalAnswers.returnsFirstArg());
    }

    private void 회원_생성(SignUpRequest 회원_가입_요청_객체) {
        assertDoesNotThrow(() -> memberService.createMember(회원_가입_요청_객체));
    }

    private void 회원_생성_의존성_동작_검증(SignUpRequest 회원_가입_요청_객체) {
        verify(memberRepo).existsByMemberId(회원_가입_요청_객체.memberId());
        verify(memberRepo).existsByEmail(회원_가입_요청_객체.email());
        verify(passwordEncoder).encode(회원_가입_요청_객체.password());
        verify(memberRepo).save(any(Member.class));
    }

    @Test
    @DisplayName("[예외]회원 생성 실패: 중복된 아이디")
    void throwMemberException_GivenDuplicatedMemberId() {
        // Given
        String 중복된_회원_아이디 = 회원_가입_요청_객체.memberId();
        given(memberRepo.existsByMemberId(중복된_회원_아이디)).willReturn(true);

        // When
        assertThatExceptionOfType(MemberException.class)
            .isThrownBy(() -> memberService.createMember(회원_가입_요청_객체))
            .extracting(MemberException::getCode, MemberException::getMessage)
            .containsExactly(
                MemberErrorCode.DUPLICATED_MEMBER_ID.name(),
                MemberErrorCode.DUPLICATED_MEMBER_ID.getMessage().formatted(중복된_회원_아이디)
            )
        ;

        // Then
        verify(memberRepo).existsByMemberId(중복된_회원_아이디);
    }

    @Test
    @DisplayName("[예외]회원 생성 실패: 중복된 이메일")
    void throwMemberException_GivenDuplicatedEmail() {
        // Given
        String 중복된_회원_이메일 = 회원_가입_요청_객체.email();
        given(memberRepo.existsByEmail(중복된_회원_이메일)).willReturn(true);

        // When
        assertThatExceptionOfType(MemberException.class)
            .isThrownBy(() -> memberService.createMember(회원_가입_요청_객체))
            .extracting(MemberException::getCode, MemberException::getMessage)
            .containsExactly(
                MemberErrorCode.DUPLICATED_EMAIL.name(),
                MemberErrorCode.DUPLICATED_EMAIL.getMessage().formatted(중복된_회원_이메일)
            )
        ;

        // Then
        verify(memberRepo).existsByEmail(중복된_회원_이메일);
    }
}
