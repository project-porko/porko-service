package io.porko.exception.response;

import io.porko.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@ToString
public class ErrorResponse {
    private final String method;
    private final String path;
    private final String code;
    private final String message;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private final LocalDateTime timestamp = LocalDateTime.now();

    private ErrorResponse(HttpServletRequest request, String code, String message) {
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.code = code;
        this.message = message;
    }

    protected ErrorResponse(HttpServletRequest request, ErrorCode errorCode) {
        this(request, errorCode.name(), errorCode.message);
    }

    private ErrorResponse(HttpServletRequest request, BusinessException exception) {
        this(request, exception.getCode(), exception.getMessage());
    }

    public static ErrorResponse businessErrorOf(HttpServletRequest request, BusinessException exception) {
        return new ErrorResponse(request, exception);
    }

    public static ErrorResponse of(HttpServletRequest request, ErrorCode errorCode) {
        return new ErrorResponse(request, errorCode);
    }
}
