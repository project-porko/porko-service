package io.porko.member.facade.dto;

import static io.porko.config.fixture.FixtureCommon.randomString;
import static io.porko.member.exception.MemberErrorCode.INVALID_VALIDATE_DUPLICATE_TARGET_FORMAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import io.porko.member.exception.MemberException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Facade:DTO:ValidateDuplicateRequestType")
class ValidateDuplicateRequestTypeTest {
    @ParameterizedTest
    @MethodSource
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @DisplayName("[예외]중복 검사 요청값의 형식이 유효하지 않은 경우")
    void throwMemberException_GivenInvalidFormatValidateDuplicatedTypeValue(String given) {
        // When & Then
        assertThatExceptionOfType(MemberException.class)
            .isThrownBy(() -> ValidateDuplicateRequestType.MEMBER_ID.validateFormat(given))
            .extracting(MemberException::getCode)
            .isEqualTo(INVALID_VALIDATE_DUPLICATE_TARGET_FORMAT.name())
        ;
    }

    private static Stream<Arguments> throwMemberException_GivenInvalidFormatValidateDuplicatedTypeValue() {
        String underLength = randomString().ofMinLength(5).sample();
        String overLength = randomString().ofMinLength(20).sample();

        return Stream.of(
            Arguments.of(underLength),
            Arguments.of(overLength)
        );
    }
}
