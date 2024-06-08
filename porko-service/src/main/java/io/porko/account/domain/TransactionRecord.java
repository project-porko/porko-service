package io.porko.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "account_holder_id",
        foreignKey = @ForeignKey(name = "FK_TRANSACTION_RECORD_TO_ACCOUNT_HOLDER"),
        nullable = false
    )
    private AccountHolder accountHolder; // 계좌주

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type; // 지출 이체 수입

    @Embedded
    private TransactionCategory category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String payType;

    @Column(nullable = false)
    private boolean isRegret;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private LocalTime transactionTime;

    public TransactionRecord(
        AccountHolder accountHolder,
        BigDecimal amount,
        TransactionType type,
        TransactionCategory category,
        String description,
        String currency,
        String payType,
        boolean isRegret,
        LocalDate transactionDate,
        LocalTime transactionTime
    ) {
        this.accountHolder = accountHolder;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.currency = currency;
        this.payType = payType;
        this.isRegret = isRegret;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
    }

    public static TransactionRecord of(
        AccountHolder accountHolder,
        BigDecimal amount,
        String transactionType,
        String category,
        String categoryDetail,
        String description,
        String currency,
        String payType,
        String isRegret,
        LocalDate transactionDate,
        String transactionTime
    ) {
        return new TransactionRecord(
            accountHolder,
            amount,
            TransactionType.valueOfName(transactionType),
            TransactionCategory.of(category, categoryDetail),
            description,
            currency,
            payType,
            !isRegret.equals("F"),
            transactionDate,
            getFormattedTransactionTime(transactionTime)
        );
    }

    private static LocalTime getFormattedTransactionTime(String transactionTime) {
        if (transactionTime.length() == 4) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("H:mm");
            return LocalTime.parse(transactionTime, dateTimeFormatter);
        } else {
            return LocalTime.parse(transactionTime, DateTimeFormatter.ISO_TIME);
        }
    }
}
