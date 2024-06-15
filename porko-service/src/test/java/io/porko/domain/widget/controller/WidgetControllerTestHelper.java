package io.porko.domain.widget.controller;

import io.porko.global.config.base.WebLayerTestBase;
import io.porko.domain.widget.controller.model.WidgetsResponse;
import io.porko.domain.widget.domain.Widget;
import io.porko.domain.widget.domain.WidgetCode;
import java.util.List;

public class WidgetControllerTestHelper extends WebLayerTestBase {
    public static List<Widget> widgets;

    static {
        widgets = List.of(
            Widget.of(1L, WidgetCode.REMAINING_BUDGET),
            Widget.of(2L, WidgetCode.UPCOMING_EXPENSES),
            Widget.of(3L, WidgetCode.LAST_MONTH_EXPENSES),
            Widget.of(4L, WidgetCode.CURRENT_MONTH_EXPENSES),
            Widget.of(5L, WidgetCode.CURRENT_MONTH_CARD_USAGE),
            Widget.of(6L, WidgetCode.MY_CHALLENGE),
            Widget.of(7L, WidgetCode.MONTHLY_EXPENSES),
            Widget.of(8L, WidgetCode.CREDIT_SCORE)
        );
    }

    public static WidgetsResponse allWidgets = WidgetsResponse.from(widgets);
}
