package io.porko.history.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HistoryErrorCode {
    HISTORY_NOT_FOUND(NOT_FOUND, "요청하신 히스토리를 찾을 수 없습니다. [historyId: %s]"),
    HISTORY_INVALID_DATE_RANGE(BAD_REQUEST, "요청하신 날짜 범위가 유효하지 않습니다. [startDate: %s, endDate: %s]"),
    NOT_MATCHED_EXPENSE_CATEGORY(INTERNAL_SERVER_ERROR, "카테고리 매칭에 실패하였습니다. [request: %s]");

    private final HttpStatus status;
    private final String message;

    HistoryErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
