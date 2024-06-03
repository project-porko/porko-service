package io.porko.widget.controller.model;

import static io.porko.widget.exception.WidgetErrorCode.DUPLICATED_SEQUENCE;
import static io.porko.widget.exception.WidgetErrorCode.INVALID_REQUEST_ORDERED_WIDGET_COUNT;

import io.porko.widget.exception.WidgetException;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ReorderWidgetRequest(
    List<ModifyMemberWidgetOrderDto> elements
) {
    public static final int ORDERED_WIDGET_COUNT = 6;

    public ReorderWidgetRequest(@NotEmpty List<ModifyMemberWidgetOrderDto> elements) {
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
}
