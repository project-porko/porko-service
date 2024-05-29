package io.porko.widget.controller;

import io.porko.config.base.presentation.WebMvcTestBase;
import io.porko.config.security.TestSecurityConfig;
import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.domain.Widget;
import io.porko.widget.domain.WidgetCode;
import io.porko.widget.service.WidgetService;
import java.util.List;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import(TestSecurityConfig.class)
public class WidgetControllerTestHelper extends WebMvcTestBase {
    @MockBean
    protected WidgetService widgetService;

    public static List<Widget> widgets;
    static {
        widgets = List.of(
            Widget.of(1L, WidgetCode.TODAY_CONSUMPTION_WEATHER),
            Widget.of(2L, WidgetCode.REMAINING_BUDGET),
            Widget.of(3L, WidgetCode.UPCOMING_EXPENSES),
            Widget.of(4L, WidgetCode.LAST_MONTH_EXPENSES),
            Widget.of(5L, WidgetCode.CURRENT_MONTH_EXPENSES),
            Widget.of(6L, WidgetCode.CURRENT_MONTH_CARD_USAGE),
            Widget.of(7L, WidgetCode.MY_CHALLENGE),
            Widget.of(8L, WidgetCode.DAILY_EXPENSES),
            Widget.of(9L, WidgetCode.CREDIT_SCORE),
            Widget.of(10L, WidgetCode.INVESTMENT_RANKING),
            Widget.of(11L, WidgetCode.STEP_COUNT),
            Widget.of(12L, WidgetCode.DAILY_HOROSCOPE)
        );
    }

    public static WidgetsResponse widgetsResponse = WidgetsResponse.from(widgets);
}
