package io.porko.widget.domain;

import io.porko.member.domain.Member;
import io.porko.widget.controller.model.ModifyMemberWidgetOrderDto;
import io.porko.widget.controller.model.ReorderWidgetRequest;
import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.exception.WidgetErrorCode;
import io.porko.widget.exception.WidgetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record OrderedMemberWidgets(
    List<MemberWidget> elements
) {
    public static OrderedMemberWidgets of(
        Member member,
        WidgetsResponse widgetsResponse,
        ReorderWidgetRequest reorderWidgetRequest
    ) {
        Map<Long, Widget> widgetMap = toMap(widgetsResponse.toWidgets());
        List<MemberWidget> memberWidgets = reorderWidgetRequest.elements().stream()
            .map(it -> processSequencedWidget(member, it, widgetMap))
            .collect(Collectors.toList());
        widgetMap.forEach((key, value) -> memberWidgets.add(processUnsequencedWidget(member, value)));

        return new OrderedMemberWidgets(memberWidgets);
    }

    private static MemberWidget processSequencedWidget(Member member, ModifyMemberWidgetOrderDto it, Map<Long, Widget> widgetMap) {
        Widget widget = widgetMap.get(it.widgetId());
        checkIsNotExistWidget(it, widget);
        widgetMap.remove(it.widgetId());
        return MemberWidget.of(member, widget, it.sequence());
    }

    private static Map<Long, Widget> toMap(List<Widget> widgets) {
        return widgets.stream()
            .collect(Collectors.toMap(Widget::getId, widget -> widget));
    }

    private static void checkIsNotExistWidget(ModifyMemberWidgetOrderDto it, Widget widget) {
        if (widget == null) {
            throw new WidgetException(WidgetErrorCode.INCLUDE_NOT_EXIST_WIDGET, it.widgetId());
        }
    }

    private static MemberWidget processUnsequencedWidget(Member member, Widget unsequencedWidget) {
        return MemberWidget.optionalOf(member, unsequencedWidget);
    }

    public int size() {
        return elements.size();
    }

    public List<MemberWidget> getSequencedWidgets() {
        return elements.stream()
            .filter(MemberWidget::isSequenced)
            .collect(Collectors.toList());
    }

    public List<MemberWidget> getUnSequencedWidgets() {
        return elements.stream()
            .filter(MemberWidget::isUnsequenced)
            .collect(Collectors.toList());
    }
}
