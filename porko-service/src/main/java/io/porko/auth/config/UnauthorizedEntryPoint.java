package io.porko.auth.config;

import static io.porko.auth.filter.TokenVerifyFilter.EXCEPTION;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.porko.auth.exception.AuthException;
import io.porko.exception.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

// TODO[#1]: TokenVerifyFilter에서 throw된 Exception을 이용한 예외 응답 처리
// TODO[#2]: EntryPoint을 이용한 예외 응답 처리 시, 한글 깨짐 방지를 위해 Deprecated된 APPLICATION_JSON_UTF8_VALUE 적용 부 수정
@RequiredArgsConstructor
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException {
        AuthException exception = (AuthException) request.getAttribute(EXCEPTION);
        ErrorResponse errorResponse = ErrorResponse.businessErrorOf(request, exception);

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
