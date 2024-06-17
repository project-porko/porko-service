package io.porko.domain.history.domain;

import io.porko.domain.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false, length = 100)
    private String place;

    @Column(nullable = false, length = 30)
    private String payType;

    @Column(nullable = false)
    private LocalDateTime usedAt;

    @Embedded
    private HistoryCategory historyCategory;

    @Column(length = 100)
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "member_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_HISTORY_TO_MEMBER")
    )
    private Member member;

    private History(
        BigDecimal cost,
        boolean isRegret,
        String place,
        String payType,
        LocalDateTime usedAt,
        HistoryCategory historyCategory,
        Member member
    ) {
        this.cost = cost;
        this.isRegret = isRegret;
        this.place = place;
        this.payType = payType;
        this.usedAt = usedAt;
        this.historyCategory = historyCategory;
        this.member = member;
    }

    public static History of(
        BigDecimal cost,
        boolean isRegret,
        String place,
        String payType,
        LocalDateTime usedAt,
        HistoryCategory historyCategory,
        Member member
    ) {
        return new History(
            cost,
            isRegret,
            place,
            payType,
            usedAt,
            historyCategory,
            member
        );
    }

    public void updateRegret(boolean isRegret) {
        this.isRegret = isRegret;
    }
}
