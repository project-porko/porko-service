package io.porko.widget.controller.model;

import io.porko.widget.domain.MemberWidget;
import io.porko.widget.domain.WidgetCode;

public record UnorderedMemberWidgetsDto(
    Long id,
    WidgetCode code,
    String description,
    int sequence
) {
    public static UnorderedMemberWidgetsDto from(MemberWidget memberWidget) {
        return new UnorderedMemberWidgetsDto(
            memberWidget.getId(),
            memberWidget.getWidgetCode(),
            memberWidget.getWidgetDescription(),
            memberWidget.getSequence()
        );
    }
}
