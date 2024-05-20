package io.porko.config.security;

import io.porko.auth.config.SecurityConfig;
import org.springframework.context.annotation.Import;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
}
