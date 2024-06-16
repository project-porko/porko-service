package io.porko.domain.member.controller.model.validateduplicate;

public record ValidateDuplicateResponse(
    ValidateDuplicateType requestType,
    String requestValue,
    boolean isDuplicated,
    AvailabilityStatus availabilityStatus
) {
    public static ValidateDuplicateResponse of(ValidateDuplicateRequest request, boolean isDuplicated) {
        return new ValidateDuplicateResponse(
            request.type(),
            request.value(),
            isDuplicated,
            AvailabilityStatus.valueOf(isDuplicated)
        );
    }
}
