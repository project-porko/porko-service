package io.porko.history.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HistoryErrorCode {
    HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 히스토리를 찾을 수 없습니다. [historyId: %s]"),
    HISTORY_INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "요청하신 날짜 범위가 유효하지 않습니다. [startDate: %s, endDate: %s]"),
    ;

    private final HttpStatus status;
    private final String message;

    HistoryErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
