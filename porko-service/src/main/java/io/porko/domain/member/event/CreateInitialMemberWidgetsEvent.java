package io.porko.domain.member.event;

import io.porko.global.config.event.DomainEvent;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateInitialMemberWidgetsEvent extends DomainEvent<Long> {
    private final Long memberId;
    private final LocalDateTime eventAt = LocalDateTime.now();

    public CreateInitialMemberWidgetsEvent(Long memberId) {
        this.memberId = memberId;
    }

    public static CreateInitialMemberWidgetsEvent from(Long memberId) {
        return new CreateInitialMemberWidgetsEvent(memberId);
    }
}
