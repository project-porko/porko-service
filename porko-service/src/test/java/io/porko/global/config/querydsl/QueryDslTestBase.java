package io.porko.global.config.querydsl;

import io.porko.config.base.persistence.JpaTestBase;
import org.springframework.context.annotation.Import;

@Import(TestQueryDslConfig.class)
public abstract class QueryDslTestBase extends JpaTestBase {
}
