package io.porko.domain.member.facade.dto;

import static io.porko.config.fixture.FixtureCommon.randomString;
import static io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateType.EMAIL;
import static io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateType.PHONE_NUMBER;
import static io.porko.domain.member.exception.MemberErrorCode.INVALID_VALIDATE_DUPLICATE_VALUE_FORMAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import io.porko.domain.member.controller.model.validateduplicate.ValidateDuplicateType;
import io.porko.domain.member.exception.MemberException;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Facade:DTO:ValidateDuplicateRequestType")
class ValidateDuplicateTypeTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @DisplayName("[예외]중복 검사 요청값의 형식이 Null, 빈값, 공백인 경우")
    void throwMemberException_GivenNullOrEmptyOrBlank(String value) {
        // When & Then
        Arrays.stream(ValidateDuplicateType.values()).forEach(it ->
            assertThatExceptionOfType(MemberException.class)
                .isThrownBy(() -> it.validateFormat(value))
                .extracting(MemberException::getErrorCode)
                .isEqualTo(INVALID_VALIDATE_DUPLICATE_VALUE_FORMAT)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("[예외]중복 검사 요청값의 형식이 유효하지 않은 경우")
    void throwMemberException_GivenInvalidMemberIdFormat(ValidateDuplicateType type, String value) {
        // When & Then
        assertThatExceptionOfType(MemberException.class)
            .isThrownBy(() -> type.validateFormat(value))
            .extracting(MemberException::getErrorCode)
            .isEqualTo(INVALID_VALIDATE_DUPLICATE_VALUE_FORMAT)
        ;
    }

    private static Stream<Arguments> throwMemberException_GivenInvalidMemberIdFormat() {
        return Stream.of(
            Arguments.of(EMAIL, randomString().alpha().sample()),
            Arguments.of(PHONE_NUMBER, randomString().numeric().ofMaxLength(10).sample()),
            Arguments.of(PHONE_NUMBER, randomString().numeric().ofMinLength(12).sample())
        );
    }
}
