package io.porko.member.facade;

import static org.assertj.core.api.Assertions.assertThat;

import io.porko.config.base.business.ServiceTestBase;
import io.porko.member.controller.model.validateduplicate.AvailabilityStatus;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateRequest;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateResponse;
import io.porko.member.controller.model.validateduplicate.ValidateDuplicateType;
import io.porko.member.facade.strategy.EmailDuplicateStrategy;
import io.porko.member.facade.strategy.PhoneNumberDuplicateStrategy;
import io.porko.member.service.MemberService;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@DisplayName("Facade:ValidateDuplicate")
@WebMvcTest(controllers = ValidateDuplicateFacade.class)
class ValidateDuplicateFacadeTest extends ServiceTestBase {
    @MockBean
    private MemberService memberService;

    @SpyBean
    PhoneNumberDuplicateStrategy phoneNumberDuplicateStrategy;

    @SpyBean
    EmailDuplicateStrategy emailDuplicateStrategy;

    private final ValidateDuplicateFacade validateDuplicateFacade;

    public ValidateDuplicateFacadeTest(ValidateDuplicateFacade validateDuplicateFacade) {
        this.validateDuplicateFacade = validateDuplicateFacade;
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("중복 검사 요청 타입별 유효성 검사 및 중복 검사 후 결과 반환")
    void isDuplicated(final ValidateDuplicateRequest given) {
        // When
        ValidateDuplicateResponse actual = validateDuplicateFacade.isDuplicated(given);

        // Then
        assertThat(actual.isDuplicated()).isFalse();
        assertThat(actual.availabilityStatus()).isEqualTo(AvailabilityStatus.AVAILABLE);
    }

    private static Stream<Arguments> isDuplicated() {
        return Stream.of(
            Arguments.of(ValidateDuplicateRequest.of(ValidateDuplicateType.EMAIL, "testMember@porko.info")),
            Arguments.of(ValidateDuplicateRequest.of(ValidateDuplicateType.PHONE_NUMBER, "01011112222"))
        );
    }
}
