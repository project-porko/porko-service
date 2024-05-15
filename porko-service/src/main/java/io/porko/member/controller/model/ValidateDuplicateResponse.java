package io.porko.member.controller.model;

import io.porko.member.facade.dto.ValidateDuplicateRequestField;
import io.porko.member.facade.dto.ValidateDuplicateRequestType;

public record ValidateDuplicateResponse(
    ValidateDuplicateRequestType requestType,
    String requestValue,
    boolean isDuplicated,
    AvailabilityStatus availabilityStatus
) {
    public static ValidateDuplicateResponse of(ValidateDuplicateRequestField requestField, boolean isDuplicated) {
        return new ValidateDuplicateResponse(
            requestField.requestType(),
            requestField.value(),
            isDuplicated,
            AvailabilityStatus.valueOf(isDuplicated)
        );
    }
}
