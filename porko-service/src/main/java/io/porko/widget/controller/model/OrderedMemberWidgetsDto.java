package io.porko.widget.controller.model;

import io.porko.widget.domain.MemberWidget;
import io.porko.widget.domain.Widget;
import io.porko.widget.domain.WidgetCode;

public record OrderedMemberWidgetsDto(
    Long widgetId,
    WidgetCode code,
    String description,
    int sequence
) {
    public static OrderedMemberWidgetsDto from(MemberWidget memberWidget) {
        Widget widget = memberWidget.getWidget();
        return new OrderedMemberWidgetsDto(
            widget.getId(),
            widget.getWidgetCode(),
            widget.getDescription(),
            memberWidget.getSequence()
        );
    }
}
