package io.porko.account.event;

import static io.porko.account.csv.CsvToJavaBeansConverter.getTransactionRecordCsvBeans;

import io.porko.account.csv.TransactionRecordCsvBean;
import io.porko.account.csv.TransactionRecordCsvBeans;
import io.porko.account.domain.AccountHolder;
import io.porko.account.domain.AccountTransaction;
import io.porko.account.domain.AccountTransactions;
import io.porko.account.domain.TransactionRecord;
import io.porko.account.repo.AccountHolderRepo;
import io.porko.account.repo.TransactionRecordRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitializeDataEventListener {
    private final AccountHolderRepo accountHolderRepo;
    private final TransactionRecordRepo transactionRecordRepo;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void init() throws Exception {
        AccountTransactions accountHolders = getAccountHolders();
        accountHolderRepo.saveAll(accountHolders.getHolders());

        for (AccountTransaction accountTransaction : accountHolders.elements()) {
            List<TransactionRecordCsvBean> givenCsvBeans = getTransactionRecordCsvBeans(
                accountTransaction.initialDataFileName(),
                TransactionRecordCsvBean.class
            );
            List<TransactionRecord> givenEntity = TransactionRecordCsvBeans.toEntity(accountTransaction.accountHolder(), givenCsvBeans);
            transactionRecordRepo.saveAllAndFlush(givenEntity);
        }
    }

    private AccountTransactions getAccountHolders() {
        return new AccountTransactions(List.of(
            new AccountTransaction(AccountHolder.of("손보리", "01011111111"), "data/sonbori.csv"),
            new AccountTransaction(AccountHolder.of("이채민", "01022222222"), "data/leechemin.csv"),
            new AccountTransaction(AccountHolder.of("이민정", "01033333333"), "data/leeminjung.csv")
        ));
    }
}
