package io.porko.domain.member.facade.strategy;

import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateType;
import io.porko.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailDuplicateStrategy implements ValidateDuplicateStrategy {
    private final MemberService memberService;

    @Override
    public ValidateDuplicateResponse isDuplicated(ValidateDuplicateRequest request) {
        return ValidateDuplicateResponse.of(
            request,
            memberService.isDuplicatedEmail(request.value())
        );
    }

    @Override
    public boolean isSupport(ValidateDuplicateType requestType) {
        return requestType == ValidateDuplicateType.EMAIL;
    }
}
