package io.porko.member.facade.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.AbstractMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Facade:DTO:ValidateDuplicateRequestField")
class ValidateDuplicateRequestFieldTest {
    @ParameterizedTest
    @MethodSource
    @DisplayName("중복 검사 요청 항목 추출")
    void create(final AbstractMap.SimpleEntry<String, Object> firstEntry, ValidateDuplicateRequestType expected) {
        // When
        ValidateDuplicateRequestField actual = ValidateDuplicateRequestField.of(firstEntry);

        // Then
        assertThat(actual).hasFieldOrPropertyWithValue("requestType", expected);
    }

    private static Stream<Arguments> create() {
        return Stream.of(
            Arguments.of(
                new AbstractMap.SimpleEntry<>("memberId", "porkoMemberId"),
                ValidateDuplicateRequestType.MEMBER_ID
            ),
            Arguments.of(new AbstractMap.SimpleEntry<>("email", "porkoMemberId@porko.info"),
                ValidateDuplicateRequestType.EMAIL
            )
        );
    }

    @Test
    @DisplayName("[예외]중복 검사 대상 항목이 아닌 경우")
    void throwException_WhenNotValidateDuplicateTargetField() {
        // Given
        AbstractMap.SimpleEntry<String, Object> firstEntry = new AbstractMap.SimpleEntry<>("invalid field", "porkoMemberId");

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> ValidateDuplicateRequestField.of(firstEntry));
    }
}
