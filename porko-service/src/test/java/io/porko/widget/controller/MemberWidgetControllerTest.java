package io.porko.widget.controller;

import static io.porko.config.security.TestSecurityConfig.TEST_PORKO_ID;
import static io.porko.config.security.TestSecurityConfig.TEST_PORKO_MEMBER_EMAIL;
import static io.porko.widget.controller.MemberWidgetController.MEMBER_WIDGET_BASE_URI;
import static io.porko.widget.fixture.MemberWidgetFixture.modifyModifyMemberWidgetsOrderRequest;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import io.porko.config.base.presentation.WebMvcTestBase;
import io.porko.config.fixture.FixtureCommon;
import io.porko.config.security.TestSecurityConfig;
import io.porko.widget.controller.model.ModifyMemberWidgetsOrderRequest;
import io.porko.widget.controller.model.OrderedMemberWidgetsResponse;
import io.porko.widget.service.MemberWidgetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MemberWidgetController.class)
@DisplayName("Controller:MemberWidget")
@Import(TestSecurityConfig.class)
class MemberWidgetControllerTest extends WebMvcTestBase {
    @MockBean
    protected MemberWidgetService memberWidgetService;

    @Test
    @WithUserDetails(value = TEST_PORKO_MEMBER_EMAIL, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[회원 위젯 순서 변경][PUT:201]")
    void modifyMemberWidgetOrder() throws Exception {
        // Given
        ModifyMemberWidgetsOrderRequest given = modifyModifyMemberWidgetsOrderRequest();
        willDoNothing().given(memberWidgetService).reorderWidget(TEST_PORKO_ID, given);

        // When
        ResultActions resultActions = put()
            .url(MEMBER_WIDGET_BASE_URI)
            .noAuthentication()
            .jsonContent(given)
            .created();

        // Then
        resultActions.andDo(print());
        verify(memberWidgetService).reorderWidget(TEST_PORKO_ID, given);
    }

    /*
     * TODO 유효하지 못한 API 요청 TC 작성
     *  - 변경 대상 위젯의 개수가 6개가 아닌 경우
     *  - 변경 대상 위젯에 중복이 포함된 경우
     *  - 변경 대상 위젯의 순서가 중복된 경우
     *  - 변경 대상 위젯에 고정 타입 위젯이 포함된 경우
     *  - 변경 대상 위젯의 순서가 1~6범위가 아닌 경우
     *  - 변경 대상 위젯에 존재하지 않는 위젯이 포함된 경우
     * */

    @Test
    @WithUserDetails(value = TEST_PORKO_MEMBER_EMAIL, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[회원 위젯 순서 조회][GET:200]")
    void getOrderedMemberWidget() throws Exception {
        OrderedMemberWidgetsResponse given = FixtureCommon.dtoType().giveMeBuilder(OrderedMemberWidgetsResponse.class).sample();

        // Given
        given(memberWidgetService.loadOrderedMemberWidgets(TEST_PORKO_ID)).willReturn(given);

        // When
        get()
            .url("/member/widget")
            .noAuthentication()
            .expect().ok();

        // Then
    }
}
