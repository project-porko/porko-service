package io.porko.exception.handler;

import io.porko.exception.BusinessException;
import io.porko.exception.response.ErrorCode;
import io.porko.exception.response.ErrorResponse;
import io.porko.exception.response.invalid.MethodArgumentNotValidErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
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

@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessException(
        BusinessException exception,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(exception.getStatus())
            .body(ErrorResponse.businessErrorOf(
                request,
                exception
            ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        return ResponseEntity.badRequest()
            .body(MethodArgumentNotValidErrorResponse.of(
                request,
                ErrorCode.METHOD_ARGUMENT_NOT_VALID,
                exception.getFieldErrors()
            ));
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
        return ResponseEntity
            .status(errorCode.status)
            .body(ErrorResponse.of(
                request,
                errorCode)
            );
    }
}
