package io.porko.widget.domain;

import io.porko.widget.controller.model.OrderedMemberWidgetsDto;
import io.porko.widget.controller.model.UnorderedMemberWidgetsDto;
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

    public List<UnorderedMemberWidgetsDto> unordered() {
        return elements.stream()
            .filter(MemberWidget::isUnsequenced)
            .map(UnorderedMemberWidgetsDto::from)
            .collect(Collectors.toList());
    }
}
