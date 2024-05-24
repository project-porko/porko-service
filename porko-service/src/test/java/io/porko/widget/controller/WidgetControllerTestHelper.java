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
            Widget.from(WidgetCode.TODAY_CONSUMPTION_WEATHER),
            Widget.from(WidgetCode.REMAINING_BUDGET),
            Widget.from(WidgetCode.UPCOMING_EXPENSES),
            Widget.from(WidgetCode.LAST_MONTH_EXPENSES),
            Widget.from(WidgetCode.CURRENT_MONTH_EXPENSES),
            Widget.from(WidgetCode.CURRENT_MONTH_CARD_USAGE),
            Widget.from(WidgetCode.MY_CHALLENGE),
            Widget.from(WidgetCode.DAILY_EXPENSES),
            Widget.from(WidgetCode.CREDIT_SCORE),
            Widget.from(WidgetCode.INVESTMENT_RANKING),
            Widget.from(WidgetCode.STEP_COUNT),
            Widget.from(WidgetCode.DAILY_HOROSCOPE)
        );
    }

    public static WidgetsResponse widgetsResponse = WidgetsResponse.from(widgets);
}
