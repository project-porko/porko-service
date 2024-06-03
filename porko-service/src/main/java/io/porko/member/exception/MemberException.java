package io.porko.member.exception;

import io.porko.exception.BusinessException;

public class MemberException extends BusinessException {
    private MemberErrorCode errorCode;
    private String message;

    public MemberException(MemberErrorCode errorCode, Object... args) {
        super(
            errorCode.getStatus(),
            errorCode.name(),
            errorCode.getMessage(),
            args
        );
    }
}
