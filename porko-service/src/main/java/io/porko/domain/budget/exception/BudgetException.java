package io.porko.domain.budget.exception;

import io.porko.exception.BusinessException;

public class BudgetException extends BusinessException {
    private BudgetErrorCode errorCode;
    private String message;

    public BudgetException(BudgetErrorCode errorCode, Object... args) {
        super(
            errorCode.getStatus(),
            errorCode.name(),
            errorCode.getMessage(),
            args
        );
    }
}
