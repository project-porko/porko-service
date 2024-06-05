package io.porko.widget.controller.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ModifyMemberWidgetOrderDto(
    Long widgetId,
    int sequence
) {
    public ModifyMemberWidgetOrderDto(
        @NotNull @Min(1) @Max(8) Long widgetId,
        @Min(1) @Max(6) int sequence
    ) {
        this.widgetId = widgetId;
        this.sequence = sequence;
    }
}
