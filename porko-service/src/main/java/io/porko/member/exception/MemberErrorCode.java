package io.porko.member.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {
    DUPLICATED_MEMBER_ID(BAD_REQUEST, "중복된 ID입니다. [request: %s]"),
    DUPLICATED_EMAIL(BAD_REQUEST, "중복된 이메일입니다. [request: %s]"),
    INVALID_VALIDATE_DUPLICATE_VALUE_FORMAT(BAD_REQUEST, "중복 검사 대상 항목의 형식이 유효하지 않습니다. [request: %s]"),
    UNSUPPORTED_VALIDATE_DUPLICATE_TYPE(BAD_REQUEST, "지원하지 않는 중복 검사 대상 항목입니다. [request: %s]"),
    ;

    private final HttpStatus status;
    private final String message;

    MemberErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String formattingErrorMessage(Object... objects) {
        return getMessage().formatted(objects);
    }
}
