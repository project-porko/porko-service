package io.porko.widget.controller.model;

import io.porko.widget.domain.Widget;
import io.porko.widget.domain.WidgetCode;
import io.porko.widget.domain.WidgetType;

public record WidgetDto(
    Long id,
    WidgetCode code,
    WidgetType type,
    String description
) {
    public static WidgetDto from(Widget widget) {
        return new WidgetDto(
            widget.getId(),
            widget.getWidgetCode(),
            widget.getWidgetCode().getType(),
            widget.getWidgetCode().getDescription()
        );
    }
}
