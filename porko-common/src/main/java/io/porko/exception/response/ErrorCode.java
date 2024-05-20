package io.porko.exception.response;

import static io.porko.utils.ConvertUtils.convertToConstants;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    HTTP_MESSAGE_NOT_READABLE(BAD_REQUEST, "요청 값을 확인 할 수 없습니다. 요청 값을 확인해 주세요."),
    METHOD_ARGUMENT_TYPE_MISMATCH(BAD_REQUEST, "요청 값의 자료형이 잘못되었습니다. 요청 값을 확인해 주세요."),
    METHOD_ARGUMENT_NOT_VALID(BAD_REQUEST, "잘못된 요청입니다. Request Body를 확인해 주세요."),
    MISSING_SERVLET_REQUEST_PARAMETER(BAD_REQUEST, "잘못된 요청입니다. Query Parameter를 확인해 주세요."),
    MISSING_SERVLET_PATH_VARIABLE(BAD_REQUEST, "잘못된 요청입니다. Path URI를 확인해 주세요."),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 Media Type입니다. Request Header의 Content-Type을 확인해주세요."),

    UNEXPECTED_ERROR(INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생하였습니다.");

    public final HttpStatus status;
    public final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorCode valueOf(Exception exception) {
        String errorCodeName = convertExceptionClassNameToErrorCodeName(exception);
        try {
            return valueOf(errorCodeName);
        } catch (IllegalArgumentException e) {
            return ErrorCode.UNEXPECTED_ERROR;
        }
    }

    private static String convertExceptionClassNameToErrorCodeName(Exception exception) {
        String className = extractExceptionClassName(exception);
        return convertToErrorCodeName(className);
    }

    private static String extractExceptionClassName(Exception exception) {
        return exception.getClass().getSimpleName();
    }

    private static String convertToErrorCodeName(String className) {
        String exceptKeyword = "_EXCEPTION";
        return convertToConstants(className).replace(exceptKeyword, "");
    }
}
