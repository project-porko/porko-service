package io.porko.widget.exception;

import io.porko.exception.BusinessException;

public class WidgetException extends BusinessException {
    private WidgetErrorCode errorCode;
    private String message;

    public WidgetException(WidgetErrorCode errorCode) {
        super(
            errorCode.getStatus(),
            errorCode.name(),
            errorCode.getMessage()
        );
    }

    public WidgetException(WidgetErrorCode errorCode, Object... args) {
        super(
            errorCode.getStatus(),
            errorCode.name(),
            errorCode.getMessage(),
            args
        );
    }
}
