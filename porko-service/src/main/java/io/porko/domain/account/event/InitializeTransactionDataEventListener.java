package io.porko.domain.account.event;

import io.porko.domain.account.csv.CsvToJavaBeansConverter;
import io.porko.domain.account.csv.TransactionRecordCsvBean;
import io.porko.domain.account.csv.TransactionRecordCsvBeans;
import io.porko.domain.account.domain.AccountTransaction;
import io.porko.domain.account.domain.AccountTransactions;
import io.porko.domain.account.domain.TransactionRecord;
import io.porko.domain.account.repo.AccountHolderRepo;
import io.porko.domain.account.repo.TransactionRecordRepo;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitializeTransactionDataEventListener {
    private final AccountHolderRepo accountHolderRepo;
    private final TransactionRecordRepo transactionRecordRepo;

    @Async
    @EventListener
    public void onInitializeTransactionData(InitializeTransactionDataEvent event) throws Exception {
        AccountTransactions accountHolders = event.accountHolders();
        accountHolderRepo.saveAll(accountHolders.getHolders());

        for (AccountTransaction accountTransaction : accountHolders.elements()) {
            List<TransactionRecordCsvBean> givenCsvBeans = getTransactionRecords(accountTransaction.initialDataFileName());
            List<TransactionRecord> givenEntity = TransactionRecordCsvBeans.toEntity(accountTransaction.accountHolder(), givenCsvBeans);
            transactionRecordRepo.saveAllAndFlush(givenEntity);
        }
    }

    private static List<TransactionRecordCsvBean> getTransactionRecords(String initialDataFileName) throws IOException {
        return CsvToJavaBeansConverter.getTransactionRecordCsvBeans(
            initialDataFileName,
            TransactionRecordCsvBean.class
        );
    }
}
