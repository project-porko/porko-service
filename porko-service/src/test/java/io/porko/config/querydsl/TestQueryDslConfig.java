package io.porko.config.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.porko.member.repo.MemberQueryRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestQueryDslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public MemberQueryRepo memberQueryRepo() {
        return new MemberQueryRepo(jpaQueryFactory());
    }
}
