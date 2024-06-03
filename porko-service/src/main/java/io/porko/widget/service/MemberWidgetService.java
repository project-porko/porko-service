package io.porko.widget.service;

import io.porko.member.domain.Member;
import io.porko.member.service.MemberService;
import io.porko.widget.controller.model.OrderedMemberWidgetsResponse;
import io.porko.widget.controller.model.ReorderWidgetRequest;
import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.domain.OrderedMemberWidgets;
import io.porko.widget.repo.MemberWidgetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberWidgetService {
    private final MemberService memberService;
    private final WidgetService widgetService;
    private final MemberWidgetRepo memberWidgetRepo;

    @Transactional
    public void reorderWidget(Long memberId, ReorderWidgetRequest request) {
        Member member = memberService.findMemberById(memberId);
        WidgetsResponse widgetsResponse = widgetService.loadAllWidgets();

        OrderedMemberWidgets orderedMemberWidgets = OrderedMemberWidgets.of(member, widgetsResponse, request);

        memberWidgetRepo.deleteByMemberId(memberId);
        memberWidgetRepo.saveAll(orderedMemberWidgets.elements());
    }

    public OrderedMemberWidgetsResponse loadOrderedMemberWidgets(Long memberId) {
        return OrderedMemberWidgetsResponse.from(memberWidgetRepo.findByMemberIdOrderBySequenceAsc(memberId));
    }
}
