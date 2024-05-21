package io.porko.member.controller.model.validateduplicate;

public record ValidateDuplicateTypeDto(
    String field,
    String description
) {
    public static ValidateDuplicateTypeDto of(ValidateDuplicateType validateDuplicateType) {
        return new ValidateDuplicateTypeDto(validateDuplicateType.name(), validateDuplicateType.getDescription());
    }
}
