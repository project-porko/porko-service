package io.porko.account.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import io.porko.account.domain.AccountHolder;
import io.porko.account.domain.TransactionRecord;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class TransactionRecordCsvBean {
    @CsvBindByName(required = true, column = "금액")
    private BigDecimal amount;

    @CsvBindByName(required = true, column = "타입")
    private String transactionType;

    @CsvBindByName(required = true, column = "대분류")
    private String category;

    @CsvBindByName(required = true, column = "소분류")
    private String categoryDetail; // 카테고리 상세

    @CsvBindByName(required = true, column = "내용")
    private String description; // 거래 내용 상세

    @CsvBindByName(required = true, column = "화폐")
    private String currency;

    @CsvBindByName(required = true, column = "결제수단")
    private String payType;

    @CsvBindByName(required = false, column = "후회소비")
    private String isRegret;

    @CsvDate("yyyy-MM-dd")
    @CsvBindByName(required = true, column = "날짜")
    private LocalDate transactionDate;

    @CsvBindByName(required = true, column = "시간")
    private String transactionTime;

    TransactionRecord toEntity(AccountHolder accountHolder) {
        return TransactionRecord.of(
            accountHolder,
            amount,
            transactionType,
            category,
            categoryDetail,
            description,
            currency,
            payType,
            isRegret,
            transactionDate,
            transactionTime
        );
    }
}
