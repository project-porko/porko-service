package io.porko.member.controller.model.validateduplicate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Model:ValidateDuplicateTypeResponse")
class ValidateDuplicateTypeResponseTest {
    @Test
    @DisplayName("ValidateDuplicateType Enum 목록 정보를 반환하기 위한 일급컬렉션 생성")
    void convertTo() {
        // When
        ValidateDuplicateTypeResponse actual = ValidateDuplicateTypeResponse.create();

        // Then
        List<ValidateDuplicateType> types = Arrays.asList(ValidateDuplicateType.values());

        assertThat(actual.elements())
            .isNotNull()
            .hasSize(types.size())
            .extracting(ValidateDuplicateTypeDto::field, ValidateDuplicateTypeDto::description)
            .containsExactlyElementsOf(
                types.stream()
                    .map(type -> Tuple.tuple(type.name(), type.getDescription()))
                    .collect(Collectors.toList())
            );
    }
}
