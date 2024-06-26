package io.porko.domain.widget.controller;

import static io.porko.global.config.security.TestSecurityConfig.TEST_MEMBER_ID;
import static io.porko.global.config.security.TestSecurityConfig.TEST_MEMBER_EMAIL;
import static io.porko.domain.widget.controller.MemberWidgetController.MEMBER_WIDGET_BASE_URI;
import static io.porko.domain.widget.fixture.MemberWidgetFixture.validReorderWidgetRequest;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import io.porko.global.config.base.WebLayerTestBase;
import io.porko.config.fixture.FixtureCommon;
import io.porko.domain.widget.controller.model.MemberWidgetsResponse;
import io.porko.domain.widget.controller.model.ReorderWidgetRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;

@DisplayName("Controller:MemberWidget")
class MemberWidgetControllerTest extends WebLayerTestBase {
    @Test
    @WithUserDetails(value = TEST_MEMBER_EMAIL, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[회원 위젯 순서 변경][PUT:201]")
    void modifyMemberWidgetOrder() throws Exception {
        // Given
        ReorderWidgetRequest given = validReorderWidgetRequest;
        willDoNothing().given(memberWidgetService).reorderWidget(TEST_MEMBER_ID, given);

        // When
        put()
            .url(MEMBER_WIDGET_BASE_URI)
            .noAuthentication()
            .jsonContent(given)
            .created();

        // Then
        verify(memberWidgetService).reorderWidget(TEST_MEMBER_ID, given);
    }

    /*
     * TODO 유효하지 못한 API 요청 TC 작성
     *  - 변경 대상 위젯의 개수가 6개가 아닌 경우
     *  - 변경 대상 위젯에 중복이 포함된 경우
     *  - 변경 대상 위젯의 순서가 중복된 경우
     *  - 변경 대상 위젯의 순서가 1~6범위가 아닌 경우
     *  - 변경 대상 위젯에 존재하지 않는 위젯이 포함된 경우
     * */

    @Test
    @WithUserDetails(value = TEST_MEMBER_EMAIL, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[회원 위젯 순서 조회][GET:200]")
    void getOrderedMemberWidget() throws Exception {
        // Given
        MemberWidgetsResponse given = FixtureCommon.dtoType()
            .giveMeBuilder(MemberWidgetsResponse.class)
            .sample();

        given(memberWidgetService.loadOrderedMemberWidgets(TEST_MEMBER_ID)).willReturn(given);

        // When & Then
        get()
            .url(MEMBER_WIDGET_BASE_URI)
            .noAuthentication()
            .expect().ok();
    }
}
