package io.porko.widget.controller;

import static io.porko.widget.controller.MemberWidgetController.MEMBER_WIDGET_BASE_URI;

import io.porko.auth.controller.model.LoginMember;
import io.porko.utils.ResponseEntityUtils;
import io.porko.widget.controller.model.ModifyMemberWidgetsOrderRequest;
import io.porko.widget.controller.model.OrderedMemberWidgetsResponse;
import io.porko.widget.service.MemberWidgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = MEMBER_WIDGET_BASE_URI,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class MemberWidgetController {
    static final String MEMBER_WIDGET_BASE_URI = "/member/widget";
    public static final String MEMBER_WIDGETS = "member/{memberId}/widgets";
    private final MemberWidgetService memberWidgetService;

    @PutMapping
    public ResponseEntity<Void> modifyMemberWidgetOrder(
        @LoginMember Long memberId,
        @Valid @RequestBody ModifyMemberWidgetsOrderRequest modifyMemberWidgetsOrderRequest
    ) {
        memberWidgetService.reorderWidget(memberId, modifyMemberWidgetsOrderRequest);
        return ResponseEntityUtils.created(MEMBER_WIDGETS, memberId);
    }

    @GetMapping
    public ResponseEntity<OrderedMemberWidgetsResponse> getOrderedMemberWidget(
        @LoginMember Long memberId
    ) {
        return ResponseEntity.ok(memberWidgetService.loadOrderedMemberWidgets(memberId));
    }
}
