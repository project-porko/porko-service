package io.porko.widget.controller.model;

import io.porko.widget.domain.MemberWidgets;
import java.util.List;

public record MemberWidgetsResponse(
    List<OrderedMemberWidgetsDto> orderedMemberWidgets,
    List<UnorderedMemberWidgetsDto> unorderedMemberWidgets
) {
    public static MemberWidgetsResponse from(MemberWidgets memberWidgets) {
        return new MemberWidgetsResponse(
            memberWidgets.ordered(),
            memberWidgets.unordered()
        );
    }
}
