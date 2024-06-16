package io.porko.domain.member.controller.model.validateduplicate;

import java.util.Arrays;
import java.util.List;

public record ValidateDuplicateTypeResponse(
    List<ValidateDuplicateTypeDto> elements
) {
    public static ValidateDuplicateTypeResponse create() {
        return new ValidateDuplicateTypeResponse(Arrays.stream(ValidateDuplicateType.values())
            .map(ValidateDuplicateTypeDto::of)
            .toList());
    }
}
