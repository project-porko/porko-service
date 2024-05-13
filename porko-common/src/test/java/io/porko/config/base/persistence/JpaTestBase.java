package io.porko.config.base.persistence;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = false)
public abstract class JpaTestBase extends DatasourceTestConfig {
}
