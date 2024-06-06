package io.porko.widget.service;

import io.porko.member.domain.Member;
import io.porko.member.service.MemberService;
import io.porko.widget.controller.model.MemberWidgetsResponse;
import io.porko.widget.controller.model.ReorderWidgetRequest;
import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.domain.MemberWidgets;
import io.porko.widget.domain.ReOrderedMemberWidgets;
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

        ReOrderedMemberWidgets reOrderedMemberWidgets = ReOrderedMemberWidgets.of(member, widgetsResponse, request);

        memberWidgetRepo.deleteByMemberId(memberId);
        memberWidgetRepo.saveAll(reOrderedMemberWidgets.elements());
    }

    public MemberWidgetsResponse loadOrderedMemberWidgets(Long memberId) {
        MemberWidgets memberWidgets = MemberWidgets.from(memberWidgetRepo.findByMemberIdOrderBySequenceAsc(memberId));
        return MemberWidgetsResponse.from(memberWidgets);
    }
}
