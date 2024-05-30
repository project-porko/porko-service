package io.porko.history.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private Boolean regret;

    @Column(nullable = false, length = 30)
    private String place;

    @Column(nullable = false, length = 30)
    private String payType;

    @Column(nullable = false)
    private LocalDateTime usedAt;

    @Embedded
    private SpendingCategory spendingCategoryId;

    @Column(nullable = false)
    private Type type;

    @Column(length = 100)
    private String memo;

    public History(
            Long memberId,
            BigDecimal cost,
            Boolean regret,
            String place,
            String payType,
            LocalDateTime usedAt,
            SpendingCategory spendingCategoryId,
            Type type,
            String memo
    ) {
        this.memberId = memberId;
        this.cost = cost;
        this.regret = regret;
        this.place = place;
        this.payType = payType;
        this.usedAt = usedAt;
        this.spendingCategoryId = spendingCategoryId;
        this.type = type;
        this.memo = memo;
    }

    public static History of(
            Long memberId,
            BigDecimal cost,
            Boolean regret,
            String place,
            String payType,
            LocalDateTime usedAt,
            SpendingCategory spendingCategoryId,
            Type type,
            String memo
    ) {
        return new History(memberId, cost, regret, place, payType, usedAt, spendingCategoryId, type, memo);
    }
}