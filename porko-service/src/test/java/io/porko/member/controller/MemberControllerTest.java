package io.porko.member.controller;

import static io.porko.member.controller.model.validateduplicate.AvailabilityStatus.AVAILABLE;
import static io.porko.member.controller.model.validateduplicate.AvailabilityStatus.UNAVAILABLE;
import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.EMAIL;
import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.LOCATION;

import io.porko.config.fixture.FixtureCommon;
import io.porko.member.controller.model.signup.SignUpRequest;
import io.porko.member.controller.model.validateduplicate.AvailabilityStatus;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("Controller:Member")
class MemberControllerTest extends MemberControllerTestHelper {
    @ParameterizedTest(name = "[{index}] 요청:[타입:{0}, 요청값:{1}], 중복 여부:{2}, 사용 가능 상태:{3}")
    @MethodSource
    @DisplayName("[GET:200]중복 검사")
    void validateDuplicate(
        ValidateDuplicateType 중복_검사_요청_타입,
        String 중복_검사_요청값,
        boolean 중복_여부,
        AvailabilityStatus 사용_가능_상태
    ) throws Exception {
        // Given
        ValidateDuplicateRequest 중복_검사_요청_객체 = ValidateDuplicateRequest.of(중복_검사_요청_타입, 중복_검사_요청값);
        given(validateDuplicateFacade.isDuplicated(중복_검사_요청_객체))
            .willReturn(ValidateDuplicateResponse.of(중복_검사_요청_객체, 중복_여부));

        // When
        ResultActions 중복_검사_요청_결과 = 중복_검사_요청(중복_검사_요청_객체.type(), 중복_검사_요청_객체.value()).ok();

        // Then
        verify(validateDuplicateFacade).isDuplicated(중복_검사_요청_객체);

        ValidateDuplicateResponse 중복_검사_요청_응답 = andReturn(중복_검사_요청_결과, ValidateDuplicateResponse.class);
        assertAll(
            () -> assertThat(중복_검사_요청_응답.requestType()).isEqualTo(중복_검사_요청_객체.type()),
            () -> assertThat(중복_검사_요청_응답.requestValue()).isEqualTo(중복_검사_요청_객체.value()),
            () -> assertThat(중복_검사_요청_응답.availabilityStatus()).isEqualTo(사용_가능_상태)
        );
    }

    private static Stream<Arguments> validateDuplicate() {
        String memberId = "porkoMemberId";
        String email = "porkoMember@porko.info";

        return Stream.of(
            Arguments.of(MEMBER_ID, memberId, true, UNAVAILABLE),
            Arguments.of(MEMBER_ID, memberId, false, AVAILABLE),
            Arguments.of(EMAIL, email, true, UNAVAILABLE),
            Arguments.of(EMAIL, email, false, AVAILABLE)
        );
    }

    @Test
    @DisplayName("[POST:201]회원 가입")
    void signUp() throws Exception {
        // Given
        SignUpRequest 회원_가입_요청_객체 = FixtureCommon.withValidated().giveMeOne(SignUpRequest.class);

        // When & Then
        회원_가입_요청(회원_가입_요청_객체).created();
        verify(memberService).createMember(회원_가입_요청_객체);
    }

    @Test
    @DisplayName("[POST:400]회원 가입 실패:값이 잘못된 요청")
    void throwException_WhenInvalidMethodArgument() throws Exception {
        // When & Then
        회원_가입_요청(유효_하지_않은_회원_가입_요청_객체).badRequest();
    }
}
