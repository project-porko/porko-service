package io.porko.domain.widget.service;

import io.porko.domain.member.domain.Member;
import io.porko.domain.member.service.MemberService;
import io.porko.domain.widget.controller.model.MemberWidgetsResponse;
import io.porko.domain.widget.controller.model.ReorderWidgetRequest;
import io.porko.domain.widget.controller.model.WidgetsResponse;
import io.porko.domain.widget.repo.MemberWidgetRepo;
import io.porko.domain.widget.domain.MemberWidgets;
import io.porko.domain.widget.domain.ReOrderedMemberWidgets;
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
