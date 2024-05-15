package io.porko.member.facade.strategy;

import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.MEMBER_ID;

import io.porko.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateType;
import io.porko.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberIdDuplicateStrategy implements ValidateDuplicateStrategy {
    private final MemberService memberService;

    @Override
    public ValidateDuplicateResponse isDuplicated(ValidateDuplicateRequest request) {
        return ValidateDuplicateResponse.of(
            request,
            memberService.isDuplicatedMemberId(request.value())
        );
    }

    @Override
    public boolean isSupport(ValidateDuplicateType requestType) {
        return requestType == MEMBER_ID;
    }
}
