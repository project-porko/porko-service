package io.porko.member.facade;

import io.porko.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.member.exception.MemberErrorCode;
import io.porko.member.exception.MemberException;
import io.porko.member.facade.strategy.ValidateDuplicateStrategy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateDuplicateFacade {
    private final List<ValidateDuplicateStrategy> validateDuplicateStrategies;

    public ValidateDuplicateResponse isDuplicated(ValidateDuplicateRequest request) {
        return validateDuplicateStrategies.stream()
            .filter(it -> it.isSupport(request.type()))
            .findAny()
            .orElseThrow(() -> new MemberException(MemberErrorCode.UNSUPPORTED_VALIDATE_DUPLICATE_TYPE, request))
            .isDuplicated(request)
            ;
    }
}
