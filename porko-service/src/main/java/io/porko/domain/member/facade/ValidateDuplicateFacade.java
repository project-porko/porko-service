package io.porko.domain.member.facade;

import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.domain.member.exception.MemberErrorCode;
import io.porko.domain.member.exception.MemberException;
import io.porko.domain.member.facade.strategy.ValidateDuplicateStrategy;
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
