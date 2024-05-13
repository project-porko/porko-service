package io.porko.exception.response.invalid;

import io.porko.exception.response.ErrorCode;
import io.porko.exception.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class MethodArgumentNotValidErrorResponse extends ErrorResponse {
    private final ErrorDetails errorDetails;

    private MethodArgumentNotValidErrorResponse(
        HttpServletRequest request,
        ErrorCode errorCode,
        ErrorDetails errorDetails
    ) {
        super(request, errorCode);
        this.errorDetails = errorDetails;
    }

    public static MethodArgumentNotValidErrorResponse of(
        HttpServletRequest request,
        ErrorCode basicErrorCode,
        List<FieldError> fieldErrors
    ) {
        return new MethodArgumentNotValidErrorResponse(
            request,
            basicErrorCode,
            ErrorDetails.from(fieldErrors)
        );
    }
}
