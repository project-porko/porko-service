package io.porko.widget.domain;

import static io.porko.widget.exception.WidgetErrorCode.INCLUDE_FIXED_WIDGET;
import static io.porko.widget.exception.WidgetErrorCode.INVALID_ORDERED_WIDGET_COUNT;

import io.porko.widget.exception.WidgetException;
import java.util.List;
import java.util.Optional;

public record OrderedMemberWidgets(
    List<MemberWidget> elements
) {
    private static final int ORDERED_WIDGET_COUNT = 6;

    public static OrderedMemberWidgets of(List<MemberWidget> memberWidgets) {
        validateOrderedTargetWidgets(memberWidgets);
        return new OrderedMemberWidgets(memberWidgets);
    }

    public static void validateOrderedTargetWidgets(List<MemberWidget> memberWidgets) {
        checkIncludeFixedWidget(memberWidgets);
        checkOrderedTargetWidgetsCount(memberWidgets);
    }

    private static void checkIncludeFixedWidget(List<MemberWidget> memberWidgets) {
        Optional<MemberWidget> fixedWidget = memberWidgets.stream()
            .filter(it -> it.getWidget().getWidgetCode().isFixed())
            .findFirst();

        if (fixedWidget.isPresent()) {
            throw new WidgetException(INCLUDE_FIXED_WIDGET);
        }
    }

    private static void checkOrderedTargetWidgetsCount(List<MemberWidget> memberWidgets) {
        if (memberWidgets.size() != ORDERED_WIDGET_COUNT) {
            throw new WidgetException(INVALID_ORDERED_WIDGET_COUNT);
        }
    }
}
