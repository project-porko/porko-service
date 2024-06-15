package io.porko.domain.member.event;

import io.porko.domain.member.domain.Member;
import io.porko.global.config.event.DomainEvent;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateHistoriesEvent extends DomainEvent<Long> {
    private final Long memberId;
    private final String phoneNumber;
    private final LocalDateTime eventAt = LocalDateTime.now();

    private CreateHistoriesEvent(Member member) {
        this.memberId = member.getId();
        this.phoneNumber = member.getPhoneNumber();
    }

    public static CreateHistoriesEvent from(Member member) {
        return new CreateHistoriesEvent(member);
    }
}
