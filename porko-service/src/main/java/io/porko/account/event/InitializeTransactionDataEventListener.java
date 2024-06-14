package io.porko.account.event;

import static io.porko.account.csv.CsvToJavaBeansConverter.getTransactionRecordCsvBeans;

import io.porko.account.csv.TransactionRecordCsvBean;
import io.porko.account.csv.TransactionRecordCsvBeans;
import io.porko.account.domain.AccountTransaction;
import io.porko.account.domain.AccountTransactions;
import io.porko.account.domain.TransactionRecord;
import io.porko.account.repo.AccountHolderRepo;
import io.porko.account.repo.TransactionRecordRepo;
import java.io.IOException;
import java.net.URISyntaxException;
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

    private static List<TransactionRecordCsvBean> getTransactionRecords(String initialDataFileName) throws URISyntaxException, IOException {
        return getTransactionRecordCsvBeans(
            initialDataFileName,
            TransactionRecordCsvBean.class
        );
    }
}
