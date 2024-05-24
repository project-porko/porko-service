package io.porko.widget.controller.model;

import io.porko.widget.domain.Widget;

public record WidgetDto(
    Long id,
    String code,
    String type,
    String description
) {
    public static WidgetDto from(Widget widget) {
        return new WidgetDto(
            widget.getId(),
            widget.getWidgetCode().name(),
            widget.getWidgetCode().getType().name(),
            widget.getWidgetCode().getDescription()
        );
    }
}
