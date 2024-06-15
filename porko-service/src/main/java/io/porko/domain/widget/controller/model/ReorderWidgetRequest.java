package io.porko.domain.widget.controller.model;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record ReorderWidgetRequest(
    List<ModifyMemberWidgetOrderDto> elements
) {
    public ReorderWidgetRequest(@NotEmpty List<ModifyMemberWidgetOrderDto> elements) {
        this.elements = elements;
    }
}
