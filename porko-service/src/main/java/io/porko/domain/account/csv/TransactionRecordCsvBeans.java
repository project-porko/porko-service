package io.porko.domain.account.csv;

import io.porko.domain.account.domain.AccountHolder;
import io.porko.domain.account.domain.TransactionRecord;
import java.util.List;

public record TransactionRecordCsvBeans(
    List<TransactionRecord> elements
) {
    public static List<TransactionRecord> toEntity(AccountHolder accountHolder, List<TransactionRecordCsvBean> transactionRecordCsvBeans) {
        return transactionRecordCsvBeans.stream()
            .map(it -> it.toEntity(accountHolder))
            .toList();
    }
}
