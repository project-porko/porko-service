package io.porko.config.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DomainEventPublisherAspect {
    private final ApplicationEventPublisher eventPublisher;

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
