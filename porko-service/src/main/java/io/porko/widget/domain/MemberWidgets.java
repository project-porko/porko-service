package io.porko.widget.domain;

import io.porko.widget.controller.model.OrderedMemberWidgetsDto;
import java.util.List;
import java.util.stream.Collectors;

public record MemberWidgets(List<MemberWidget> elements) {
    public static MemberWidgets from(List<MemberWidget> elements) {
        return new MemberWidgets(elements);
    }

    public List<OrderedMemberWidgetsDto> ordered() {
        return elements.stream()
            .filter(MemberWidget::isSequenced)
            .map(OrderedMemberWidgetsDto::from)
            .collect(Collectors.toList());
    }

    public List<OrderedMemberWidgetsDto> unordered() {
        return elements.stream()
            .filter(MemberWidget::isUnsequenced)
            .map(OrderedMemberWidgetsDto::from)
            .collect(Collectors.toList());
    }
}
