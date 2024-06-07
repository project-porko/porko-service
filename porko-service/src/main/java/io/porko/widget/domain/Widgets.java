package io.porko.widget.domain;

import java.util.Arrays;
import java.util.List;

public record Widgets(
    List<Widget> elements
) {
    public static Widgets initialWidgets() {
        return new Widgets(Arrays.stream(WidgetCode.values())
            .map(Widget::from)
            .toList());
    }
}
