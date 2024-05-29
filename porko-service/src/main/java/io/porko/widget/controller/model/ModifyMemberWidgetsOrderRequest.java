package io.porko.widget.controller.model;

import static io.porko.widget.exception.WidgetErrorCode.DUPLICATED_SEQUENCE;
import static io.porko.widget.exception.WidgetErrorCode.INCLUDE_FIXED_WIDGET;
import static io.porko.widget.exception.WidgetErrorCode.INCLUDE_NOT_EXIST_WIDGET;
import static io.porko.widget.exception.WidgetErrorCode.INVALID_REQUEST_ORDERED_WIDGET_COUNT;

import io.porko.member.domain.Member;
import io.porko.widget.domain.OrderedMemberWidgets;
import io.porko.widget.domain.Widget;
import io.porko.widget.exception.WidgetException;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record ModifyMemberWidgetsOrderRequest(
    List<ModifyMemberWidgetOrderDto> elements
) {
    public static final int ORDERED_WIDGET_COUNT = 6;

    public ModifyMemberWidgetsOrderRequest(@NotEmpty List<ModifyMemberWidgetOrderDto> elements) {
        validateRequest(elements);
        this.elements = elements;
    }

    private void validateRequest(List<ModifyMemberWidgetOrderDto> elements) {
        validateOrderedWidgetCount(elements);
        validateSequenceRange(elements);
    }

    private static void validateOrderedWidgetCount(List<ModifyMemberWidgetOrderDto> elements) {
        if (elements.size() != ORDERED_WIDGET_COUNT) {
            throw new WidgetException(INVALID_REQUEST_ORDERED_WIDGET_COUNT);
        }
    }

    private static void validateSequenceRange(List<ModifyMemberWidgetOrderDto> elements) {
        Set<Integer> sequenceSet = elements.stream()
            .map(ModifyMemberWidgetOrderDto::sequence)
            .collect(Collectors.toSet());

        if (hasDuplicateSequence(sequenceSet)) {
            throw new WidgetException(DUPLICATED_SEQUENCE, elements);
        }
    }

    private static boolean hasDuplicateSequence(Set<Integer> sequenceSet) {
        return sequenceSet.size() != ORDERED_WIDGET_COUNT;
    }

    public OrderedMemberWidgets toEntity(Member member, List<Widget> targetWidgets) {
        validateTargetWidgets(targetWidgets);

        Map<Long, Widget> targetWidgetMap = targetWidgets.stream()
            .collect(Collectors.toMap(Widget::getId, widget -> widget));

        return OrderedMemberWidgets.of(elements.stream()
            .map(it -> it.toEntity(member, targetWidgetMap))
            .collect(Collectors.toList()));
    }

    private void validateTargetWidgets(List<Widget> targetWidgets) {
        validateIncludeFixedWidget(targetWidgets);
        validateIncludeNotExistWidget(targetWidgets);
    }

    private static void validateIncludeFixedWidget(List<Widget> targetWidgets) {
        Optional<Widget> fixedWidget = targetWidgets.stream()
            .filter(it -> it.getWidgetCode().isFixed())
            .findFirst();

        if (fixedWidget.isPresent()) {
            throw new WidgetException(INCLUDE_FIXED_WIDGET);
        }
    }

    private void validateIncludeNotExistWidget(List<Widget> targetWidgets) {
        if (targetWidgets.size() != ORDERED_WIDGET_COUNT) {
            throw new WidgetException(INCLUDE_NOT_EXIST_WIDGET);
        }
    }

    public List<Long> extractWidgetIds() {
        return elements.stream()
            .map(ModifyMemberWidgetOrderDto::widgetId)
            .toList();
    }
}
