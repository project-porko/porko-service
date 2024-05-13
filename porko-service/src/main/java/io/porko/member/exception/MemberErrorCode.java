package io.porko.member.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {
    DUPLICATED_MEMBER_ID(HttpStatus.BAD_REQUEST, "중복된 ID입니다. [request: %s]"),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다. [request: %s]"),
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
