package io.porko.global.config.event;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class DomainEventPublisherAspect {
    private final ApplicationEventPublisher eventPublisher;

    // TODO Transactional이 관련된 Domain Event 처리 개선 해보기 https://wildeveloperetrain.tistory.com/246
    // TODO https://velog.io/@ssssujini99/SpringSpringBoot-%EC%9D%B4%EB%B2%A4%ED%8A%B8%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4-%EB%8F%84%EB%A9%94%EC%9D%B8-%EA%B0%84-%EA%B2%B0%ED%95%A9%EB%8F%84-%EB%82%AE%EC%B6%94%EA%B8%B0
    // TODO https://mandykr.tistory.com/80
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object handleEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        DomainEventPublisher.setPublisher(eventPublisher);
        try {
            return joinPoint.proceed();
        } finally {
            DomainEventPublisher.reset();
        }
    }
}
