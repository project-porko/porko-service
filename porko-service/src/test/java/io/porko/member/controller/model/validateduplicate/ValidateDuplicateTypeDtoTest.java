package io.porko.member.controller.model.validateduplicate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Model:ValidateDuplicateTypeDto")
class ValidateDuplicateTypeDtoTest {
    @ParameterizedTest
    @MethodSource
    @DisplayName("ValidateDuplicateType Enum 정보를 반환하기 위한 DTO 생성")
    void create(ValidateDuplicateType given) {
        // When
        ValidateDuplicateTypeDto actual = ValidateDuplicateTypeDto.of(given);

        // Then
        assertThat(actual)
            .extracting(ValidateDuplicateTypeDto::field, ValidateDuplicateTypeDto::description)
            .containsExactly(given.name(), given.getDescription());
    }

    private static Stream<Arguments> create() {
        return Stream.of(
            Arguments.of(ValidateDuplicateType.EMAIL),
            Arguments.of(ValidateDuplicateType.PHONE_NUMBER)
        );
    }
}
