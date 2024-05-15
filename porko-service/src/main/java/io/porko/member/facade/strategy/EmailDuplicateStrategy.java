package io.porko.member.facade.strategy;

import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.EMAIL;

import io.porko.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateType;
import io.porko.member.service.MemberService;
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
        return requestType == EMAIL;
    }
}
