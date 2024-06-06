package io.porko.widget.service;

import static io.porko.config.security.TestSecurityConfig.TEST_MEMBER_ID;
import static io.porko.config.security.TestSecurityConfig.testMember;
import static io.porko.widget.controller.WidgetControllerTestHelper.allWidgets;
import static io.porko.widget.domain.ReOrderedMemberWidgets.ORDERED_WIDGET_COUNT;
import static io.porko.widget.fixture.MemberWidgetFixture.validReorderWidgetRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import io.porko.config.base.business.ServiceTestBase;
import io.porko.member.service.MemberService;
import io.porko.widget.controller.model.MemberWidgetsResponse;
import io.porko.widget.controller.model.ReorderWidgetRequest;
import io.porko.widget.domain.MemberWidget;
import io.porko.widget.domain.Widget;
import io.porko.widget.repo.MemberWidgetRepo;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("Service:MemberWidget")
class MemberWidgetServiceTest extends ServiceTestBase {
    @Mock
    MemberService memberService;

    @Mock
    WidgetService widgetService;

    @Mock
    MemberWidgetRepo memberWidgetRepo;

    @InjectMocks
    MemberWidgetService memberWidgetService;

    @Test
    @DisplayName("회원 위젯 순서 변경")
    void reorderWidget() {
        // Given
        final Long memberId = 1L;
        given(memberService.findMemberById(memberId)).willReturn(testMember);
        given(widgetService.loadAllWidgets()).willReturn(allWidgets);

        willDoNothing().given(memberWidgetRepo).deleteByMemberId(memberId);
        given(memberWidgetRepo.saveAll(anyCollection())).willReturn(null);

        ReorderWidgetRequest given = validReorderWidgetRequest;

        // When
        memberWidgetService.reorderWidget(memberId, given);

        // Then
        verify(memberService).findMemberById(memberId);
        verify(widgetService).loadAllWidgets();
        verify(memberWidgetRepo).deleteByMemberId(memberId);
        verify(memberWidgetRepo).saveAll(anyCollection());
    }

    @Test
    @DisplayName("회원 위젯 순서 조회")
    void loadOrderedMemberWidgets() {
        // Given
        List<MemberWidget> given = getMemberWidgets();
        given(memberWidgetRepo.findByMemberIdOrderBySequenceAsc(TEST_MEMBER_ID)).willReturn(given);

        // When
        MemberWidgetsResponse actual = memberWidgetService.loadOrderedMemberWidgets(TEST_MEMBER_ID);

        // Then
        assertThat(actual.orderedMemberWidgets()).hasSize(ORDERED_WIDGET_COUNT);
        assertThat(actual.unorderedMemberWidgets()).hasSize(allWidgets.size() - ORDERED_WIDGET_COUNT);

        verify(memberWidgetRepo).findByMemberIdOrderBySequenceAsc(TEST_MEMBER_ID);
    }

    private static List<MemberWidget> getMemberWidgets() {
        List<MemberWidget> given = new ArrayList<>();
        List<Widget> widgets = allWidgets.toWidgets();
        for (int i = 0; i < widgets.size(); i++) {
            int sequence = i + 1;
            if (i > ORDERED_WIDGET_COUNT - 1) {
                given.add(MemberWidget.unorderedOf(testMember, widgets.get(i)));
            } else {
                given.add(MemberWidget.of(testMember, widgets.get(i), sequence));
            }
        }
        return given;
    }
}
