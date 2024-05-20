package io.porko.config.jpa;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// TODO: Test 환경에서 사용할 사용자 정보를 통해 AuditingListener가 설정할 메타 데이터 제공
@TestConfiguration
@EnableJpaAuditing(modifyOnCreate = false)
public class TestJpaConfig {
}
