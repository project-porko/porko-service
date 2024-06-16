package io.porko.domain.widget.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import io.porko.config.fixture.FixtureCommon;
import io.porko.domain.member.controller.model.signup.SignUpRequest;
import io.porko.domain.member.event.CreateInitialMemberWidgetsEvent;
import io.porko.domain.member.service.MemberService;
import io.porko.global.config.base.EventListenerTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@DisplayName("Event:CreateInitialMemberWidgetsEvent")
class CreateInitialMemberWidgetsEventListenerTest extends EventListenerTestBase {
    private final MemberService memberService;

    public CreateInitialMemberWidgetsEventListenerTest(MemberService memberService) {
        this.memberService = memberService;
    }

    // TODO WebMvcTest로 변경
    @Test
    @DisplayName("회원 가입 완료 시, 초기화된 회원 위젯 목록 생성")
    void onCompleteSignupCreateInitialMemberWidgets() {
        // Given
        SignUpRequest request = FixtureCommon.withValidated().giveMeOne(SignUpRequest.class);

        // When
        memberService.createMember(request);

        // Then
        assertThat(events.stream(CreateInitialMemberWidgetsEvent.class).count()).isEqualTo(1);
        ArgumentCaptor<CreateInitialMemberWidgetsEvent> eventCaptor = ArgumentCaptor.forClass(CreateInitialMemberWidgetsEvent.class);
        verify(createInitialMemberWidgetsEventListener).onCompleteSignupCreateInitialMemberWidgets(eventCaptor.capture());
    }
}
