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
            .map(it -> tt(accountHolder, it))
            .collect(Collectors.toList());
    }

    private static TransactionRecord tt(AccountHolder accountHolder, TransactionRecordCsvBean transactionRecordCsvBean) {
        return TransactionRecord.of(
            accountHolder,
            transactionRecordCsvBean.getAmount(),
            transactionRecordCsvBean.getTransactionType(),
            transactionRecordCsvBean.getCategory(),
            transactionRecordCsvBean.getCategoryDetail(),
            transactionRecordCsvBean.getDescription(),
            transactionRecordCsvBean.getCurrency(),
            transactionRecordCsvBean.getPayType(),
            transactionRecordCsvBean.getIsRegret(),
            transactionRecordCsvBean.getTransactionDate(),
            transactionRecordCsvBean.getTransactionTime()
        );
    }
}
