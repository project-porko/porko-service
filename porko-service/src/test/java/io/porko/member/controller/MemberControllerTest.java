package io.porko.member.controller;

import static org.mockito.Mockito.verify;

import io.porko.config.fixture.FixtureCommon;
import io.porko.member.controller.model.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Controller:Member")
class MemberControllerTest extends MemberControllerTestHelper {
    @Test
    @DisplayName("[POST:201]회원 가입")
    void signUp() throws Exception {
        // Given
        SignUpRequest 회원_가입_요청_객체 = FixtureCommon.withValidated().giveMeOne(SignUpRequest.class);

        // When & Then
        회원_가입_요청(회원_가입_요청_객체).created();
        verify(memberService).createMember(회원_가입_요청_객체);
    }

    @Test
    @DisplayName("[POST:400]회원 가입 실패:값이 잘못된 요청")
    void throwException_WhenInvalidMethodArgument() throws Exception {
        // When & Then
        회원_가입_요청(유효_하지_않은_회원_가입_요청_객체).badRequest();
    }
}
