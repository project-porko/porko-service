package io.porko.auth.config;

import io.porko.auth.config.jwt.JwtProperties;
import io.porko.auth.filter.TokenVerifyFilter;
import io.porko.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headersConfigurer -> headersConfigurer
                .frameOptions(FrameOptionsConfig::disable)
            )
            .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // [TODO:Refactor] endpoint, roles에 따른 인가 정책 가독성 개선을 위한 AuthenticationEndpointByRoles 적용
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/member/sign-up", "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/member/validate").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                new TokenVerifyFilter(authService, jwtProperties),
                UsernamePasswordAuthenticationFilter.class
            )
            .build();
    }
}
