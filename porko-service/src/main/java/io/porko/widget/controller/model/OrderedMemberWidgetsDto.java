package io.porko.widget.controller.model;

import io.porko.widget.domain.MemberWidget;
import io.porko.widget.domain.WidgetCode;

public record OrderedMemberWidgetsDto(
    Long id,
    WidgetCode code,
    String description,
    int sequence
) {
    public static OrderedMemberWidgetsDto from(MemberWidget memberWidget) {
        return new OrderedMemberWidgetsDto(
            memberWidget.getId(),
            memberWidget.getWidgetCode(),
            memberWidget.getWidgetDescription(),
            memberWidget.getSequence()
        );
    }
}
