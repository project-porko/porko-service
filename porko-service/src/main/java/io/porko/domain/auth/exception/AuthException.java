package io.porko.domain.auth.exception;

import io.porko.exception.BusinessException;
import lombok.Getter;

@Getter
public class AuthException extends BusinessException {
    private final AuthErrorCode errorCode;

    public AuthException(AuthErrorCode errorCode) {
        super(
            errorCode.getStatus(),
            errorCode.name(),
            errorCode.getMessage()
        );
        this.errorCode = errorCode;
    }
}
