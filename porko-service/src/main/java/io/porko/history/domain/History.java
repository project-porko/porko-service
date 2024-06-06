package io.porko.history.domain;

import io.porko.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false, length = 100)
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public History(
            BigDecimal cost,
            Boolean regret,
            String place,
            String payType,
            LocalDateTime usedAt,
            SpendingCategory spendingCategoryId,
            String memo,
            Member member
    ) {
        this.cost = cost;
        this.regret = regret;
        this.place = place;
        this.payType = payType;
        this.usedAt = usedAt;
        this.spendingCategoryId = spendingCategoryId;
        this.memo = memo;
        this.member = member;
    }

    public History(
            BigDecimal cost,
            Boolean regret,
            String place,
            String payType,
            LocalDateTime usedAt,
            SpendingCategory spendingCategoryId
    ) {
        this.cost = cost;
        this.regret = regret;
        this.place = place;
        this.payType = payType;
        this.usedAt = usedAt;
        this.spendingCategoryId = spendingCategoryId;
    }

    public static History of(
            BigDecimal cost,
            Boolean regret,
            String place,
            String payType,
            LocalDateTime usedAt,
            SpendingCategory spendingCategoryId,
            String memo,
            Member member
    ) {
        return new History(cost, regret, place, payType, usedAt, spendingCategoryId, memo, member);
    }

    public static History of(
            BigDecimal cost,
            Boolean regret,
            String place,
            String payType,
            LocalDateTime usedAt,
            SpendingCategory spendingCategoryId
    ) {
        return new History(cost, regret, place, payType, usedAt, spendingCategoryId);
    }

    public void updateRegret(Boolean regret) {
        this.regret = regret;
    }
}
