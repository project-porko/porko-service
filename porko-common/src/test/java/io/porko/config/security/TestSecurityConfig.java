package io.porko.config.security;

import org.springframework.context.annotation.Import;

// TODO: Mocking을 통해 인증된 사용자 정보 제공
@Import(SecurityConfig.class)
public class TestSecurityConfig {
}
