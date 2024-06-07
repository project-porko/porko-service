package io.porko.widget.domain;

import io.porko.member.domain.Member;
import io.porko.widget.controller.model.OrderedMemberWidgetsDto;
import java.util.List;
import java.util.stream.Collectors;

public record MemberWidgets(List<MemberWidget> elements) {
    public static MemberWidgets from(List<MemberWidget> elements) {
        return new MemberWidgets(elements);
    }

    public static MemberWidgets initialOf(Member member, Widgets widgets) {
        List<MemberWidget> list = widgets.elements()
            .stream()
            .map(it -> createMemberWidgetByType(member, it))
            .toList();
        return new MemberWidgets(list);
    }

    private static MemberWidget createMemberWidgetByType(Member member, Widget widget) {
        if (widget.isDefault()) {
            return MemberWidget.of(member, widget, widget.getInitialSequence());
        }
        return MemberWidget.unorderedOf(member, widget);
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
