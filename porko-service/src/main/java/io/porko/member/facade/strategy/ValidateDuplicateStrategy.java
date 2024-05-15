package io.porko.member.facade.strategy;

import io.porko.member.controller.model.ValidateDuplicateResponse;
import io.porko.member.facade.dto.ValidateDuplicateRequestField;
import io.porko.member.facade.dto.ValidateDuplicateRequestType;

public interface ValidateDuplicateStrategy {
    ValidateDuplicateResponse isDuplicated(ValidateDuplicateRequestField requestField);

    boolean isSupport(ValidateDuplicateRequestType requestType);
}
