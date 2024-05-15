package io.porko.member.facade.dto;

import static io.porko.config.fixture.FixtureCommon.randomAlpha;
import static io.porko.config.fixture.FixtureCommon.randomString;
import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.EMAIL;
import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.MEMBER_ID;
import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.PHONE_NUMBER;
import static io.porko.member.exception.MemberErrorCode.INVALID_VALIDATE_DUPLICATE_VALUE_FORMAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import io.porko.member.controller.model.validateduplicate.ValidateDuplicateType;
import io.porko.member.exception.MemberException;
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
                .extracting(MemberException::getCode)
                .isEqualTo(INVALID_VALIDATE_DUPLICATE_VALUE_FORMAT.name())
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("[예외]중복 검사 요청값의 형식이 유효하지 않은 경우")
    void throwMemberException_GivenInvalidMemberIdFormat(ValidateDuplicateType type, String value) {
        // When & Then
        assertThatExceptionOfType(MemberException.class)
            .isThrownBy(() -> type.validateFormat(value))
            .extracting(MemberException::getCode)
            .isEqualTo(INVALID_VALIDATE_DUPLICATE_VALUE_FORMAT.name())
        ;
    }

    private static Stream<Arguments> throwMemberException_GivenInvalidMemberIdFormat() {
        return Stream.of(
            Arguments.of(MEMBER_ID, randomAlpha().ofMaxLength(5).sample()),
            Arguments.of(MEMBER_ID, randomAlpha().ofMinLength(21).sample()),
            Arguments.of(EMAIL, randomString().alpha().sample()),
            Arguments.of(PHONE_NUMBER, randomString().numeric().ofMaxLength(10).sample()),
            Arguments.of(PHONE_NUMBER, randomString().numeric().ofMinLength(12).sample())
        );
    }
}
