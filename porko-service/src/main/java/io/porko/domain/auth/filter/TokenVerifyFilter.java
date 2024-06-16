package io.porko.domain.auth.filter;

import static io.porko.domain.auth.domain.TokenResolver.resolve;

import io.porko.domain.auth.controller.model.PorkoPrincipal;
import io.porko.domain.auth.domain.JwtProperties;
import io.porko.domain.auth.exception.AuthErrorCode;
import io.porko.domain.auth.exception.AuthException;
import io.porko.domain.auth.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class TokenVerifyFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String EXCEPTION = "exception";

    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        try {
            final String authorizationToken = extractAuthorizationToken(request);
            PorkoPrincipal porkoPrincipal = resolve(authorizationToken, jwtProperties);
            verifyPrincipal(porkoPrincipal);
            processRegisterAuthentication(porkoPrincipal, request);
        } catch (AuthException e) {
            request.setAttribute(EXCEPTION, e);
        }
        chain.doFilter(request, response);
    }

    private String extractAuthorizationToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isNotExistAuthorizationToken(authorizationHeader)) {
            throw new AuthException(AuthErrorCode.BAD_CREDENTIALS);
        }
        return extractToken(authorizationHeader);
    }

    private boolean isNotExistAuthorizationToken(String header) {
        return header == null || !header.startsWith(BEARER_PREFIX);
    }

    private static String extractToken(String header) {
        return header.split(" ")[1].trim();
    }

    private void verifyPrincipal(PorkoPrincipal porkoPrincipal) {
        authService.loadUserByUsername(porkoPrincipal.getUsername());
    }

    private void processRegisterAuthentication(PorkoPrincipal porkoPrincipal, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            porkoPrincipal,
            null,
            porkoPrincipal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    }
}
