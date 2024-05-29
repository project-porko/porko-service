package io.porko.widget.service;

import io.porko.member.domain.Member;
import io.porko.member.service.MemberService;
import io.porko.widget.controller.model.ModifyMemberWidgetsOrderRequest;
import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.domain.OrderedMemberWidgets;
import io.porko.widget.domain.Widget;
import io.porko.widget.repo.MemberWidgetRepo;
import java.util.List;
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
    public void reorderWidget(Long memberId, ModifyMemberWidgetsOrderRequest request) {
        Member member = memberService.findMemberById(memberId);
        WidgetsResponse widgetsResponse = widgetService.loadAllWidgets();
        List<Widget> targetWidgets = widgetsResponse.extractByIds(request.extractWidgetIds());

        OrderedMemberWidgets orderedMemberWidgets = request.toEntity(member, targetWidgets);

        memberWidgetRepo.deleteByMemberId(memberId);
        memberWidgetRepo.saveAll(orderedMemberWidgets.elements());
    }
}
