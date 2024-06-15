package io.porko.domain.widget.domain;

import io.porko.domain.member.domain.Member;
import io.porko.domain.widget.controller.model.ModifyMemberWidgetOrderDto;
import io.porko.domain.widget.controller.model.ReorderWidgetRequest;
import io.porko.domain.widget.controller.model.WidgetsResponse;
import io.porko.domain.widget.exception.WidgetErrorCode;
import io.porko.domain.widget.exception.WidgetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record ReOrderedMemberWidgets(
    List<MemberWidget> elements
) {
    public static final int ORDERED_WIDGET_COUNT = 6;

    public static ReOrderedMemberWidgets of(
        Member member,
        WidgetsResponse widgetsResponse,
        ReorderWidgetRequest reorderWidgetRequest
    ) {
        validateRequest(reorderWidgetRequest);
        Map<Long, Widget> widgetMap = toMap(widgetsResponse.toWidgets());
        List<MemberWidget> memberWidgets = reorderWidgetRequest.elements().stream()
            .map(it -> processSequencedWidget(member, it, widgetMap))
            .collect(Collectors.toList());
        widgetMap.forEach((key, value) -> memberWidgets.add(processUnsequencedWidget(member, value)));

        return new ReOrderedMemberWidgets(memberWidgets);
    }

    private static void validateRequest(ReorderWidgetRequest reorderWidgetRequest) {
        List<ModifyMemberWidgetOrderDto> elements = reorderWidgetRequest.elements();
        validateOrderedWidgetCount(elements);
        validateSequenceDuplicated(elements);
    }

    private static void validateOrderedWidgetCount(List<ModifyMemberWidgetOrderDto> elements) {
        if (elements.size() != ORDERED_WIDGET_COUNT) {
            throw new WidgetException(WidgetErrorCode.INVALID_REQUEST_ORDERED_WIDGET_COUNT);
        }
    }

    private static void validateSequenceDuplicated(List<ModifyMemberWidgetOrderDto> elements) {
        Set<Integer> sequenceSet = elements.stream()
            .map(ModifyMemberWidgetOrderDto::sequence)
            .collect(Collectors.toSet());

        if (hasDuplicateSequence(sequenceSet)) {
            throw new WidgetException(WidgetErrorCode.DUPLICATED_SEQUENCE, elements);
        }
    }

    private static boolean hasDuplicateSequence(Set<Integer> sequenceSet) {
        return sequenceSet.size() != ORDERED_WIDGET_COUNT;
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
        return MemberWidget.unorderedOf(member, unsequencedWidget);
    }

    public int size() {
        return elements.size();
    }

    public List<MemberWidget> getSequencedWidgets() {
        return elements.stream()
            .filter(MemberWidget::isSequenced)
            .toList();
    }

    public List<MemberWidget> getUnSequencedWidgets() {
        return elements.stream()
            .filter(MemberWidget::isUnsequenced)
            .toList();
    }
}
