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
    private boolean isRegret;

    @Column(nullable = false, length = 30)
    private String place;

    @Column(nullable = false, length = 30)
    private String payType;

    @Column(nullable = false)
    private LocalDateTime usedAt;

    @Embedded
    private HistoryCategory historyCategoryId;

    @Column(nullable = false, length = 100)
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public History(
            Long id,
            BigDecimal cost,
            boolean isRegret,
            String place,
            String payType,
            LocalDateTime usedAt,
            HistoryCategory historyCategoryId,
            String memo,
            Member member
    ) {
        this.id = id;
        this.cost = cost;
        this.isRegret = isRegret;
        this.place = place;
        this.payType = payType;
        this.usedAt = usedAt;
        this.historyCategoryId = historyCategoryId;
        this.memo = memo;
        this.member = member;
    }

    public History(
            BigDecimal cost,
            boolean isRegret,
            String place,
            String payType,
            LocalDateTime usedAt,
            HistoryCategory historyCategoryId
    ) {
        this.cost = cost;
        this.isRegret = isRegret;
        this.place = place;
        this.payType = payType;
        this.usedAt = usedAt;
        this.historyCategoryId = historyCategoryId;
    }

    public static History of(
            Long id,
            BigDecimal cost,
            boolean isRegret,
            String place,
            String payType,
            LocalDateTime usedAt,
            HistoryCategory historyCategoryId,
            String memo,
            Member member
    ) {
        return new History(id, cost, isRegret, place, payType, usedAt, historyCategoryId, memo, member);
    }

    public static History of(
            BigDecimal cost,
            boolean isRegret,
            String place,
            String payType,
            LocalDateTime usedAt,
            HistoryCategory historyCategoryId
    ) {
        return new History(cost, isRegret, place, payType, usedAt, historyCategoryId);
    }

    public void updateRegret(boolean isRegret) {
        this.isRegret = isRegret;
    }
}
