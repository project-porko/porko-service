package io.porko.widget.exception;

import io.porko.exception.BusinessException;
import lombok.Getter;

@Getter
public class WidgetException extends BusinessException {
    private final WidgetErrorCode errorCode;

    public WidgetException(WidgetErrorCode errorCode) {
        super(
            errorCode.getStatus(),
            errorCode.name(),
            errorCode.getMessage()
        );
        this.errorCode = errorCode;
    }

    public WidgetException(WidgetErrorCode errorCode, Object... args) {
        super(
            errorCode.getStatus(),
            errorCode.name(),
            errorCode.getMessage(),
            args
        );
        this.errorCode = errorCode;
    }
}
