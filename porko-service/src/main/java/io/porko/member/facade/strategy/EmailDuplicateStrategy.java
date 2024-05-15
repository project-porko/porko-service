package io.porko.member.facade.strategy;

import io.porko.member.controller.model.ValidateDuplicateResponse;
import io.porko.member.facade.dto.ValidateDuplicateRequestField;
import io.porko.member.facade.dto.ValidateDuplicateRequestType;
import io.porko.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailDuplicateStrategy implements ValidateDuplicateStrategy {
    private final MemberService memberService;

    @Override
    public ValidateDuplicateResponse isDuplicated(ValidateDuplicateRequestField requestField) {
        return ValidateDuplicateResponse.of(
            requestField,
            memberService.isDuplicatedEmail(requestField.value())
        );
    }

    @Override
    public boolean isSupport(ValidateDuplicateRequestType requestType) {
        return requestType.isEmail();
    }
}
