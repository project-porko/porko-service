package io.porko.domain.widget.event;

import io.porko.domain.member.domain.Member;
import io.porko.domain.member.event.CreateInitialMemberWidgetsEvent;
import io.porko.domain.member.service.MemberService;
import io.porko.domain.widget.controller.model.WidgetsResponse;
import io.porko.domain.widget.service.WidgetService;
import io.porko.domain.widget.domain.MemberWidgets;
import io.porko.domain.widget.domain.Widget;
import io.porko.domain.widget.domain.Widgets;
import io.porko.domain.widget.repo.MemberWidgetRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateInitialMemberWidgetsEventListener {
    private final MemberService memberService;
    private final MemberWidgetRepo memberWidgetRepo;
    private final WidgetService widgetService;

    @Async
    @EventListener
    public void onCompleteSignupCreateInitialMemberWidgets(CreateInitialMemberWidgetsEvent event) {
        log.info("event published! : {}", event);
        Member member = memberService.findMemberById(event.getMemberId());
        WidgetsResponse widgetsResponse = widgetService.loadAllWidgets();
        List<Widget> widgets = widgetsResponse.toWidgets();
        MemberWidgets memberWidgets = MemberWidgets.initialOf(member, Widgets.from(widgets));

        memberWidgetRepo.saveAll(memberWidgets.elements());
        log.info("event complete! : {}", event);
    }
}
