package io.porko.domain.member.facade.strategy;

import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateType;

public interface ValidateDuplicateStrategy {
    ValidateDuplicateResponse isDuplicated(ValidateDuplicateRequest request);

    boolean isSupport(ValidateDuplicateType requestType);
}
