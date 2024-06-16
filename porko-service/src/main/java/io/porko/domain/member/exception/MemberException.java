package io.porko.domain.member.exception;

import io.porko.exception.BusinessException;
import lombok.Getter;

@Getter
public class MemberException extends BusinessException {
    private final MemberErrorCode errorCode;

    public MemberException(MemberErrorCode errorCode, Object... args) {
        super(
            errorCode.getStatus(),
            errorCode.name(),
            errorCode.getMessage(),
            args
        );
        this.errorCode = errorCode;
    }
}
