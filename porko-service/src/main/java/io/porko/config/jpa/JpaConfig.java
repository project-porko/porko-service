package io.porko.config.jpa;

import static org.springframework.security.config.Elements.ANONYMOUS;

import io.porko.auth.controller.model.PorkoPrincipal;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing(modifyOnCreate = false)
public class JpaConfig {
    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(this::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(PorkoPrincipal.class::cast)
            .map(PorkoPrincipal::getId);
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication.isAuthenticated() && isNotAnonymousUser(authentication);
    }

    private boolean isNotAnonymousUser(Authentication authentication) {
        return !authentication.getName().contains(ANONYMOUS);
    }
}
