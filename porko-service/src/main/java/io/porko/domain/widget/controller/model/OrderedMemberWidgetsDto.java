package io.porko.domain.widget.controller.model;

import io.porko.domain.widget.domain.MemberWidget;
import io.porko.domain.widget.domain.Widget;
import io.porko.domain.widget.domain.WidgetCode;

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
