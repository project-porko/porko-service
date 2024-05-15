package io.porko.member.facade.strategy;

import io.porko.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateType;

public interface ValidateDuplicateStrategy {
    ValidateDuplicateResponse isDuplicated(ValidateDuplicateRequest request);

    boolean isSupport(ValidateDuplicateType requestType);
}
