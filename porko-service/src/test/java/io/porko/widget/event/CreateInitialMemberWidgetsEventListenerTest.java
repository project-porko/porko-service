package io.porko.widget.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import io.porko.config.base.TestBase;
import io.porko.config.fixture.FixtureCommon;
import io.porko.member.controller.model.signup.SignUpRequest;
import io.porko.member.event.CreateInitialMemberWidgetsEvent;
import io.porko.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
@DisplayName("Event:CreateInitialMemberWidgetsEvent")
class CreateInitialMemberWidgetsEventListenerTest extends TestBase {
    private final MemberService memberService;

    @SpyBean
    private CreateInitialMemberWidgetsEventListener createInitialMemberWidgetsEventListener;

    public CreateInitialMemberWidgetsEventListenerTest(MemberService memberService) {
        this.memberService = memberService;
    }

    @Test
    @DisplayName("회원 가입 완료 시, 초기화된 회원 위젯 목록 생성")
    void onCompleteSignupCreateInitialMemberWidgets() {
        // Given
        SignUpRequest request = FixtureCommon.withValidated().giveMeOne(SignUpRequest.class);

        // When
        memberService.createMember(request);

        // Then
        verify(createInitialMemberWidgetsEventListener).onCompleteSignupCreateInitialMemberWidgets(any(CreateInitialMemberWidgetsEvent.class));
    }

    @Test
    @DisplayName("회원 가입 실패 시, 회원 위젯 초기화 이벤트는 발생하지 않는다.")
    void notPublishedSignUpCompleteEvent_WhenSignupFailed() {
        // Given

        // When

        // Then
    }

}
