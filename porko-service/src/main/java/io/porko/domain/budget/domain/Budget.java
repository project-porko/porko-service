package io.porko.domain.budget.domain;

import io.porko.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private BigDecimal goalCost;

    @Column(nullable = false)
    private Integer goalYear;

    @Column(nullable = false)
    private Integer goalMonth;

    private Budget(
            Member member,
            BigDecimal goalCost,
            Integer goalYear,
            Integer goalMonth
    ) {
        this.member = member;
        this.goalCost = goalCost;
        this.goalYear = goalYear;
        this.goalMonth = goalMonth;
    }

    public static Budget of(
            Member member,
            BigDecimal goalCost,
            Integer goalYear,
            Integer goalMonth
    ) {
        return new Budget(member, goalCost, goalYear, goalMonth);
    }
}
