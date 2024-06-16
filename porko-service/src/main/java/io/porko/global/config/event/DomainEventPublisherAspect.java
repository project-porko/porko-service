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
