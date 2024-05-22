package io.porko.member.controller;

import static io.porko.config.security.TestSecurityConfig.TEST_PORKO_MEMBER_EMAIL;
import static io.porko.config.security.TestSecurityConfig.testMember;
import static io.porko.config.security.TestSecurityConfig.testPorkoPrincipal;
import static io.porko.member.controller.model.validateduplicate.AvailabilityStatus.AVAILABLE;
import static io.porko.member.controller.model.validateduplicate.AvailabilityStatus.UNAVAILABLE;
import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.EMAIL;
import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.MEMBER_ID;
import static io.porko.member.controller.model.validateduplicate.ValidateDuplicateType.PHONE_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import io.porko.config.fixture.FixtureCommon;
import io.porko.config.security.TestSecurityConfig;
import io.porko.member.controller.model.MemberResponse;
import io.porko.member.controller.model.signup.SignUpRequest;
import io.porko.member.controller.model.validateduplicate.AvailabilityStatus;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("Controller:Member")
@Import(TestSecurityConfig.class)
class MemberControllerTest extends MemberControllerTestHelper {
    @Test
    @DisplayName("[중복 검사 가능 항목 조회][GET:200]")
    void validateDuplicateTypes() throws Exception {
        // When
        ResultActions resultActions = get()
            .url("/member/validate/types")
            .noAuthentication()
            .expect().ok();

        // Then
        List<String> expectedFields = Arrays.stream(ValidateDuplicateType.values())
            .map(Enum::name)
            .toList();

        List<String> expectedDescriptions = Arrays.stream(ValidateDuplicateType.values())
            .map(ValidateDuplicateType::getDescription)
            .toList();

        resultActions.andExpect(jsonPath("$.elements[*]").isArray())
            .andExpect(jsonPath("$.elements[*].field", hasItems(expectedFields.toArray(new String[0]))))
            .andExpect(jsonPath("$.elements[*].description", hasItems(expectedDescriptions.toArray(new String[0]))));
    }

    @ParameterizedTest(name = "[{index}] 요청:[타입:{0}, 요청값:{1}], 중복 여부:{2}, 사용 가능 상태:{3}")
    @MethodSource
    @DisplayName("[회원 가입 항목 중복 검사][GET:200]")
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
        String phoneNumber = "01012345678";

        return Stream.of(
            Arguments.of(MEMBER_ID, memberId, true, UNAVAILABLE),
            Arguments.of(MEMBER_ID, memberId, false, AVAILABLE),
            Arguments.of(EMAIL, email, true, UNAVAILABLE),
            Arguments.of(EMAIL, email, false, AVAILABLE),
            Arguments.of(PHONE_NUMBER, phoneNumber, true, UNAVAILABLE),
            Arguments.of(PHONE_NUMBER, phoneNumber, false, AVAILABLE)
        );
    }

    @Test
    @DisplayName("[회원 가입][POST:201]")
    void signUp() throws Exception {
        // Given
        SignUpRequest 회원_가입_요청_객체 = FixtureCommon.withValidated().giveMeOne(SignUpRequest.class);
        long anyLong = anyLong();
        given(memberService.createMember(회원_가입_요청_객체)).willReturn(anyLong);

        // When
        ResultActions resultActions = 회원_가입_요청(회원_가입_요청_객체).created();

        // Then
        assertThat(extractResponseHeader(resultActions, LOCATION)).isEqualTo("/member/" + anyLong);
        verify(memberService).createMember(회원_가입_요청_객체);
    }

    @Test
    @DisplayName("[회원 가입][POST:400]요청값이 유효하지 않은 경우")
    void throwException_WhenInvalidMethodArgument() throws Exception {
        // When & Then
        회원_가입_요청(유효_하지_않은_회원_가입_요청_객체).badRequest();
    }

    @Test
    @WithUserDetails(value = TEST_PORKO_MEMBER_EMAIL, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[내 정보 조회][GET:200]")
    void me() throws Exception {
        // Given
        given(memberService.loadMemberById(testPorkoPrincipal.getId()))
            .willReturn(MemberResponse.of(testMember));

        // When & Then
        내_정보_조회().ok();
        verify(memberService).loadMemberById(testPorkoPrincipal.getId());
    }

    @Test
    @WithAnonymousUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[내 정보 조회][GET:403]로그인 사용자 정보가 없는 경우")
    void throwAuthException_WhenNotExistAuthentication() throws Exception {
        // When & Then
        내_정보_조회().unAuthorized();
    }

    @Test
    @DisplayName("[내 정보 조회][GET:403]엑세스 토큰 형식이 유효하지 않은 경우")
    void throwAuthException_WhenInvalidAccessTokenFormat() throws Exception {
        // When & Then
        get()
            .url("/member/me")
            .authentication("aaa.aaaa.aaaa")
            .expect().unAuthorized();
    }
}
