package io.porko.widget.event;

import io.porko.widget.domain.Widgets;

public record CreateInitialWidgetsEvent(
    Widgets widgets
) {
    public static CreateInitialWidgetsEvent initialWidgetsEvent() {
        Widgets widgets = Widgets.initialWidgets();
        return new CreateInitialWidgetsEvent(widgets);
    }
}
