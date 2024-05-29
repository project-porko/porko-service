package io.porko.widget.controller.model;

import io.porko.widget.domain.MemberWidget;
import java.util.List;
import java.util.stream.Collectors;

public record OrderedMemberWidgetsResponse(
    List<OrderedMemberWidgetsDto> elements
) {
    public static OrderedMemberWidgetsResponse from(List<MemberWidget> orderedMemberWidget) {
        return new OrderedMemberWidgetsResponse(orderedMemberWidget.stream()
            .map(OrderedMemberWidgetsDto::from)
            .collect(Collectors.toList()));
    }
}
