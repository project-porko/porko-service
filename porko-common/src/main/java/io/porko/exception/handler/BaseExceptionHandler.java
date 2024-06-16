package io.porko.exception.handler;

import io.porko.exception.BusinessException;
import io.porko.exception.response.ErrorCode;
import io.porko.exception.response.ErrorResponse;
import io.porko.exception.response.invalid.MethodArgumentNotValidErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// TODO: 예외 발생 시, 알림 발생 이벤트 발행
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessException(
        BusinessException exception,
        HttpServletRequest request
    ) {
        ErrorResponse errorResponse = ErrorResponse.businessErrorOf(
            request,
            exception
        );

        log.error(errorResponse.toString());
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        MethodArgumentNotValidErrorResponse methodArgumentNotValidErrorResponse = MethodArgumentNotValidErrorResponse.of(
            request,
            ErrorCode.METHOD_ARGUMENT_NOT_VALID,
            exception.getFieldErrors()
        );

        log.error(methodArgumentNotValidErrorResponse.toString());
        return ResponseEntity.badRequest().body(methodArgumentNotValidErrorResponse);
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class,
        HttpRequestMethodNotSupportedException.class,
        HttpMediaTypeNotAcceptableException.class,
        HttpMediaTypeNotSupportedException.class,
        MissingPathVariableException.class,
        MissingServletRequestParameterException.class
    })
    public ResponseEntity<ErrorResponse> invalidRequestException(Exception exception, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.valueOf(exception);
        ErrorResponse errorResponse = ErrorResponse.of(
            request,
            errorCode);

        log.error(errorResponse.toString());
        return ResponseEntity.status(errorCode.status).body(errorResponse);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> unexpectedException(Exception exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(request, ErrorCode.UNEXPECTED_ERROR);
        exception.printStackTrace();
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
