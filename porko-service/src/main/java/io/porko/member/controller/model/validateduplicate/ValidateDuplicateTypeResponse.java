package io.porko.member.controller.model.validateduplicate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record ValidateDuplicateTypeResponse(
    List<ValidateDuplicateTypeDto> elements
) {
    public static ValidateDuplicateTypeResponse create() {
        return new ValidateDuplicateTypeResponse(Arrays.stream(ValidateDuplicateType.values())
            .map(ValidateDuplicateTypeDto::of)
            .collect(Collectors.toList()));
    }
}
