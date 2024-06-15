package io.porko.domain.auth.controller;

import static io.porko.domain.auth.exception.AuthErrorCode.BAD_CREDENTIALS;
import static io.porko.global.config.security.TestSecurityConfig.TEST_MEMBER_EMAIL;
import static io.porko.global.config.security.TestSecurityConfig.testMember;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import io.porko.domain.auth.controller.model.LoginRequest;
import io.porko.domain.auth.controller.model.LoginResponse;
import io.porko.domain.auth.exception.AuthException;
import io.porko.global.config.base.WebLayerTestBase;
import io.porko.config.base.presentation.RequestBuilder.Expect;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Controller:Auth")
class AuthControllerTest extends WebLayerTestBase {
    private static final String LOGIN_URI = "/login";
    private static final LoginRequest loginRequest = new LoginRequest(TEST_MEMBER_EMAIL, "password");

    @Test
    @DisplayName("[로그인][POST:200]")
    void login() throws Exception {
        // Given
        given(authService.authenticate(loginRequest))
            .willReturn(LoginResponse.of(testMember, "access token"));

        // When & Then
        로그인_요청().ok();
        verify(authService).authenticate(loginRequest);
    }

    @Disabled
    @Test
    @DisplayName("[로그인][POST:400]요청값이 올바르지 않은 경우")
    void throwAuthException_WhenInvalidRequest() throws Exception {
        // Given
        LoginRequest invalidLoginRequest = new LoginRequest("", "");

        // When & Then
        post()
            .url(LOGIN_URI)
            .noAuthentication()
            .jsonContent(invalidLoginRequest)
            .badRequest();
    }

    @Disabled
    @Test
    @DisplayName("[로그인][POST:401]사용자 정보가 존재하지 않는 경우")
    void throwAuthException_WhenMemberNotExist() throws Exception {
        // Given
        given(authService.authenticate(loginRequest))
            .willThrow(new AuthException(BAD_CREDENTIALS));

        // When & Then
        로그인_요청().unAuthorized();
        verify(authService).authenticate(loginRequest);
    }

    @Disabled
    @Test
    @DisplayName("[로그인][POST:401]비밀번호가 일치하지 않는 경우")
    void throwAuthException_WhenNotMatchedPassword() throws Exception {
        // Given
        given(authService.authenticate(loginRequest))
            .willThrow(new AuthException(BAD_CREDENTIALS));

        // When & Then
        로그인_요청().unAuthorized();
        verify(authService).authenticate(loginRequest);
    }

    private Expect 로그인_요청() {
        return post()
            .url(LOGIN_URI)
            .noAuthentication()
            .jsonContent(AuthControllerTest.loginRequest);
    }
}
