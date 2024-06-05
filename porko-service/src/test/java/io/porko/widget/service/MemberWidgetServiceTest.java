package io.porko.widget.service;

import static io.porko.config.security.TestSecurityConfig.TEST_PORKO_ID;
import static io.porko.config.security.TestSecurityConfig.testMember;
import static io.porko.widget.controller.WidgetControllerTestHelper.allWidgets;
import static io.porko.widget.fixture.MemberWidgetFixture.validReorderWidgetRequest;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import com.navercorp.fixturemonkey.api.type.TypeReference;
import io.porko.config.base.business.ServiceTestBase;
import io.porko.config.fixture.FixtureCommon;
import io.porko.member.service.MemberService;
import io.porko.widget.controller.model.ReorderWidgetRequest;
import io.porko.widget.domain.MemberWidget;
import io.porko.widget.repo.MemberWidgetRepo;
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
        List<MemberWidget> sample = FixtureCommon.entityType()
            .giveMeBuilder(new TypeReference<List<MemberWidget>>() {
            })
            .sample();

        given(memberWidgetRepo.findByMemberIdOrderBySequenceAsc(TEST_PORKO_ID)).willReturn(sample);

        // When
        memberWidgetService.loadOrderedMemberWidgets(TEST_PORKO_ID);

        // Then
        verify(memberWidgetRepo).findByMemberIdOrderBySequenceAsc(TEST_PORKO_ID);
    }
}
