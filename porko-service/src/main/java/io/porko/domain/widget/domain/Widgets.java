package io.porko.domain.widget.domain;

import java.util.Arrays;
import java.util.List;

public record Widgets(
    List<Widget> elements
) {
    public static Widgets from(List<Widget> elements) {
        return new Widgets(elements);
    }

    public static Widgets initialWidgets() {
        List<Widget> elements = Arrays.stream(WidgetCode.values())
            .map(Widget::from)
            .toList();
        return new Widgets(elements);
    }
}
