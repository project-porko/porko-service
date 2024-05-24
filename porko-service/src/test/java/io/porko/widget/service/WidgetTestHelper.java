package io.porko.widget.service;

import io.porko.widget.domain.Widget;
import io.porko.widget.domain.WidgetCode;
import java.util.List;

public class WidgetTestHelper {
    static List<Widget> widgets = List.of(
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
