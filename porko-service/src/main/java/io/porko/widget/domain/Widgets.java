package io.porko.widget.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record Widgets(
    List<Widget> elements
) {
    public static Widgets from(List<Widget> elements) {
        return new Widgets(elements);
    }

    public static Widgets initialWidgets() {
        List<Widget> elements = Arrays.stream(WidgetCode.values())
            .map(Widget::from)
            .collect(Collectors.toList());
        return new Widgets(elements);
    }
}
