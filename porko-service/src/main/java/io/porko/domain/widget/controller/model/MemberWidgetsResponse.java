package io.porko.domain.widget.controller.model;

import io.porko.domain.widget.domain.MemberWidgets;
import java.util.List;

public record MemberWidgetsResponse(
    List<OrderedMemberWidgetsDto> orderedMemberWidgets,
    List<OrderedMemberWidgetsDto> unorderedMemberWidgets
) {
    public static MemberWidgetsResponse from(MemberWidgets memberWidgets) {
        return new MemberWidgetsResponse(
            memberWidgets.ordered(),
            memberWidgets.unordered()
        );
    }
}
