package io.porko.widget.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum WidgetErrorCode {
    INVALID_REQUEST_ORDERED_WIDGET_COUNT(HttpStatus.BAD_REQUEST, "순서를 변경할 6개의 위젯을 요청하세요. [request: %s]"),
    INVALID_ORDERED_WIDGET_COUNT(HttpStatus.BAD_REQUEST, "순서 변경 시 6개의 위젯 정보가 필요합니다. 순서를 변경할 6개의 위젯을 선택하세요. [request: %s]"),
    DUPLICATED_WIDGET(HttpStatus.BAD_REQUEST, "중복된 위젯이 포함되었습니다. [request: %s]"),
    DUPLICATED_SEQUENCE(HttpStatus.BAD_REQUEST, "위젯 순서가 중복되었습니다.[request: %s]"),
    OUT_OF_SEQUENCE_RANGE(HttpStatus.BAD_REQUEST, "1~6의 순서를 선택하세요 [request: %s]"),
    INCLUDE_NOT_EXIST_WIDGET(HttpStatus.BAD_REQUEST, "존재하지 않는 위젯이 포함되었습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    WidgetErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
