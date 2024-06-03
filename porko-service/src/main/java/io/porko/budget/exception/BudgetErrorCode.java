package io.porko.budget.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BudgetErrorCode {
    BUDGET_NOT_SET(HttpStatus.NOT_FOUND, "사용자가 목표 금액을 설정하지 않았습니다. [request: %s]");

    private final HttpStatus status;
    private final String message;

    public String formattingErrorMessage(Object... objects) {
        return getMessage().formatted(objects);
    }
}
