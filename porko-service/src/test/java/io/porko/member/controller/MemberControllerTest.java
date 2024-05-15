package io.porko.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import io.porko.config.fixture.FixtureCommon;
import io.porko.member.controller.model.AvailabilityStatus;
import io.porko.member.controller.model.SignUpRequest;
import io.porko.member.controller.model.ValidateDuplicateRequest;
import io.porko.member.controller.model.ValidateDuplicateResponse;
import io.porko.member.facade.dto.ValidateDuplicateRequestField;
import io.porko.member.facade.dto.ValidateDuplicateRequestType;
import java.util.AbstractMap.SimpleEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("Controller:Member")
class MemberControllerTest extends MemberControllerTestHelper {

    @Test
    @DisplayName("[GET:200]회원 아이디 중복 검사 : 아이디가 중복되지 않은 경우")
    void validateMemberIdDuplicate_WhenNotDuplicatedMemberId() throws Exception {
        // Given
        String 중복_검사_요청_항목_명 = "memberId";
        String 중복_검사_요청_항목 = "porkoId";
        ValidateDuplicateRequest 중복_검사_요청_객체 = ValidateDuplicateRequest.of(중복_검사_요청_항목, null);
        ValidateDuplicateRequestField 중목_검사_요청_항목 = ValidateDuplicateRequestField.of(new SimpleEntry<>(중복_검사_요청_항목_명, 중복_검사_요청_항목));

        given(validateDuplicateFacade.isDuplicated(중복_검사_요청_객체))
            .willReturn(ValidateDuplicateResponse.of(중목_검사_요청_항목, false));

        // When
        ResultActions 중복_검사_요청_결과 = 중복_검사_요청(중복_검사_요청_항목_명, 중복_검사_요청_항목).ok();

        // Then
        verify(validateDuplicateFacade).isDuplicated(중복_검사_요청_객체);

        ValidateDuplicateResponse 중복_검사_요청_응답 = andReturn(중복_검사_요청_결과, ValidateDuplicateResponse.class);
        assertAll(
            () -> assertThat(중복_검사_요청_응답.requestType()).isEqualTo(ValidateDuplicateRequestType.MEMBER_ID),
            () -> assertThat(중복_검사_요청_응답.requestValue()).isEqualTo(중복_검사_요청_항목),
            () -> assertThat(중복_검사_요청_응답.isDuplicated()).isFalse(),
            () -> assertThat(중복_검사_요청_응답.availabilityStatus()).isEqualTo(AvailabilityStatus.AVAILABLE)
        );
    }

    @Test
    @DisplayName("[GET:200]회원 아이디 중복 검사 : 아이디가 중복된 경우")
    void validateMemberIdDuplicate_WhenDuplicatedMemberId() throws Exception {
        // Given
        String 중복_검사_요청_항목_명 = "memberId";
        String 중복_검사_요청_항목 = "porkoId";
        ValidateDuplicateRequest 중복_검사_요청_객체 = ValidateDuplicateRequest.of(중복_검사_요청_항목, null);
        ValidateDuplicateRequestField 중목_검사_요청_항목 = ValidateDuplicateRequestField.of(new SimpleEntry<>(중복_검사_요청_항목_명, 중복_검사_요청_항목));

        given(validateDuplicateFacade.isDuplicated(중복_검사_요청_객체))
            .willReturn(ValidateDuplicateResponse.of(중목_검사_요청_항목, true));

        // When
        ResultActions 중복_검사_요청_결과 = 중복_검사_요청(중복_검사_요청_항목_명, 중복_검사_요청_항목).ok();

        // Then
        verify(validateDuplicateFacade).isDuplicated(중복_검사_요청_객체);

        ValidateDuplicateResponse validateDuplicateResponse = andReturn(중복_검사_요청_결과, ValidateDuplicateResponse.class);
        assertAll(
            () -> assertThat(validateDuplicateResponse.requestType()).isEqualTo(ValidateDuplicateRequestType.MEMBER_ID),
            () -> assertThat(validateDuplicateResponse.requestValue()).isEqualTo(중복_검사_요청_항목),
            () -> assertThat(validateDuplicateResponse.isDuplicated()).isTrue(),
            () -> assertThat(validateDuplicateResponse.availabilityStatus()).isEqualTo(AvailabilityStatus.UNAVAILABLE)
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
