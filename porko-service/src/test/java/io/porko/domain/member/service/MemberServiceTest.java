package io.porko.domain.member.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import io.porko.config.base.business.ServiceTestBase;
import io.porko.config.fixture.FixtureCommon;
import io.porko.domain.member.controller.model.signup.SignUpRequest;
import io.porko.domain.member.domain.Member;
import io.porko.domain.member.exception.MemberErrorCode;
import io.porko.domain.member.exception.MemberException;
import io.porko.domain.member.repo.MemberRepo;
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
        given(memberRepo.existsByEmail(signUpRequest.email())).willReturn(false);
        given(memberRepo.save(any(Member.class))).will(AdditionalAnswers.returnsFirstArg());
    }

    private void 회원_생성(SignUpRequest 회원_가입_요청_객체) {
        assertDoesNotThrow(() -> memberService.createMember(회원_가입_요청_객체));
    }

    private void 회원_생성_의존성_동작_검증(SignUpRequest 회원_가입_요청_객체) {
        verify(memberRepo).existsByEmail(회원_가입_요청_객체.email());
        verify(passwordEncoder).encode(회원_가입_요청_객체.password());
        verify(memberRepo).save(any(Member.class));
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
            .extracting(MemberException::getErrorCode, MemberException::getMessage)
            .containsExactly(
                MemberErrorCode.DUPLICATED_EMAIL,
                MemberErrorCode.DUPLICATED_EMAIL.getMessage().formatted(중복된_회원_이메일)
            )
        ;

        // Then
        verify(memberRepo).existsByEmail(중복된_회원_이메일);
    }

    @Test
    @DisplayName("[예외]회원 생성 실패: 중복된 핸드폰 번호")
    void throwMemberException_GivenDuplicatedPhoneNumber() {
        // Given
        String 중복된_회원_휴대폰_번호 = 회원_가입_요청_객체.phoneNumber();
        given(memberRepo.existsByPhoneNumber(중복된_회원_휴대폰_번호)).willReturn(true);

        // When
        assertThatExceptionOfType(MemberException.class)
            .isThrownBy(() -> memberService.createMember(회원_가입_요청_객체))
            .extracting(MemberException::getErrorCode, MemberException::getMessage)
            .containsExactly(
                MemberErrorCode.DUPLICATED_PHONE_NUMBER,
                MemberErrorCode.DUPLICATED_PHONE_NUMBER.getMessage().formatted(중복된_회원_휴대폰_번호)
            )
        ;

        // Then
        verify(memberRepo).existsByPhoneNumber(중복된_회원_휴대폰_번호);
    }
}
