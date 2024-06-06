package io.porko.history.exception;

import io.porko.exception.BusinessException;
import lombok.Getter;

@Getter
public class HistoryException extends BusinessException {
    private final HistoryErrorCode errorCode;

    public HistoryException(HistoryErrorCode errorCode){
        super(
                errorCode.getStatus(),
                errorCode.name(),
                errorCode.getMessage()
        );
        this.errorCode = errorCode;
    }

    public HistoryException(HistoryErrorCode errorCode, Object...args) {
        super(
                errorCode.getStatus(),
                errorCode.name(),
                errorCode.getMessage(),
                args
        );
        this.errorCode = errorCode;
    }

}
