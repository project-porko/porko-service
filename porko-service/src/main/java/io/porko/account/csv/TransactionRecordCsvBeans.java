package io.porko.account.csv;

import io.porko.account.domain.AccountHolder;
import io.porko.account.domain.TransactionRecord;
import java.util.List;
import java.util.stream.Collectors;

public record TransactionRecordCsvBeans(
    List<TransactionRecord> elements
) {
    public static List<TransactionRecord> toEntity(AccountHolder accountHolder, List<TransactionRecordCsvBean> transactionRecordCsvBeans) {
        return transactionRecordCsvBeans.stream()
            .map(it -> it.toEntity(accountHolder))
            .collect(Collectors.toList());
    }
}
