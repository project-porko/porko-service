package io.porko.account.event;

import io.porko.account.domain.AccountHolder;
import io.porko.account.domain.AccountTransaction;
import io.porko.account.domain.AccountTransactions;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitializeTransactionDataEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void onCompleteApplicationReady() {
        applicationEventPublisher.publishEvent(InitializeTransactionDataEvent.from(getAccountHolders()));
    }

    public static AccountTransactions getAccountHolders() {
        return new AccountTransactions(List.of(
            new AccountTransaction(AccountHolder.of("손보리", "01011111111"), "data/sonbori.csv"),
            new AccountTransaction(AccountHolder.of("이채민", "01022222222"), "data/leechemin.csv"),
            new AccountTransaction(AccountHolder.of("이민정", "01033333333"), "data/leeminjung.csv")
        ));
    }
}
