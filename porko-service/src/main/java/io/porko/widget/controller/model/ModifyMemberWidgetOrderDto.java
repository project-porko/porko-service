package io.porko.widget.controller.model;

import static io.porko.widget.domain.OrderedMemberWidgets.ORDERED_WIDGET_COUNT;
import static io.porko.widget.exception.WidgetErrorCode.INVALID_SEQUENCE_RANGE;

import io.porko.widget.exception.WidgetException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ModifyMemberWidgetOrderDto(
    Long widgetId,
    int sequence
) {
    private static final int SEQUENCE_START = 1;
    private static final int SEQUENCE_END = ORDERED_WIDGET_COUNT;

    public ModifyMemberWidgetOrderDto(
        @NotNull @Min(2) @Max(12) Long widgetId,
        @Min(1) @Max(6) int sequence
    ) {
        validateSequenceRange(sequence);
        this.widgetId = widgetId;
        this.sequence = sequence;
    }

    private static void validateSequenceRange(int sequence) {
        if (isNotSequenceRange(sequence)) {
            throw new WidgetException(INVALID_SEQUENCE_RANGE, sequence);
        }
    }

    private static boolean isNotSequenceRange(Integer sequence) {
        return sequence < SEQUENCE_START || sequence > SEQUENCE_END;
    }
}
