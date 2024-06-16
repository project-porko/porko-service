package io.porko.domain.auth.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode {
    BAD_CREDENTIALS(UNAUTHORIZED, "사용자 정보가 잘못 되었습니다."),
    INVALID_SIGNATURE(UNAUTHORIZED, "서명 정보가 잘못 되었습니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "토큰 유효기간이 만료되었습니다."),
    INVALID_TOKEN_FORMAT(UNAUTHORIZED, "토큰 형식이 올바르지 않습니다."),
    UNSUPPORTED_AUDIENCE(UNAUTHORIZED, "지원하지 않는 사용자 입니다."),
    ;

    private final HttpStatus status;
    private final String message;

    AuthErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
