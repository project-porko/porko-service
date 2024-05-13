package io.porko.config.jpa;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@TestConfiguration
@EnableJpaAuditing(modifyOnCreate = false)
public class TestJpaConfiguration {
}
