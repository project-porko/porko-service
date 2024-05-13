package io.porko.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnexpectedExceptionHandler {
    // TODO: 예상하지 못한 예외 발생 시, 알림 발생 이벤트 발행 및 printStackTrace() 제거
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> unexpectedException(Exception exception, HttpServletRequest request) {
        exception.printStackTrace();
        return ResponseEntity.internalServerError()
            .body(null);
    }
}
