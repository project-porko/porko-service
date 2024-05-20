package io.porko.auth.exception;

import io.porko.exception.BusinessException;

public class AuthException extends BusinessException {
    private AuthErrorCode errorCode;
    private String message;

    public AuthException(AuthErrorCode errorCode) {
        super(
            errorCode.getStatus(),
            errorCode.name(),
            errorCode.getMessage()
        );
    }
}
