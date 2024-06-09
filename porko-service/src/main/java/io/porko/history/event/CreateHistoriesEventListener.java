package io.porko.history.event;

import io.porko.account.domain.TransactionRecord;
import io.porko.account.repo.TransactionRecordRepo;
import io.porko.history.domain.Histories;
import io.porko.history.repo.HistoryRepo;
import io.porko.member.domain.Member;
import io.porko.member.event.CreateHistoriesEvent;
import io.porko.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateHistoriesEventListener {
    private final MemberService memberService;
    private final TransactionRecordRepo transactionRecordRepo;
    private final HistoryRepo historyRepo;

    @Async
    @EventListener
    public void onCompleteSignupCreateHistories(CreateHistoriesEvent event) {
        log.info("create histories event published! : {}", event);
        Member member = memberService.findMemberById(event.getMemberId());
        List<TransactionRecord> byAccountHolderPhoneNumber = transactionRecordRepo.findByAccountHolder_PhoneNumber(event.getPhoneNumber());
        Histories histories = Histories.of(member, byAccountHolderPhoneNumber);
        historyRepo.saveAll(histories.elements());

        log.info("histories event complete! : {}", event);
    }
}
