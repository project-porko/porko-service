package io.porko.widget.domain;

import java.util.List;

public record Widgets(
    List<Widget> elements
) {
    public static Widgets from(List<Widget> elements) {
        return new Widgets(elements);
    }
}
