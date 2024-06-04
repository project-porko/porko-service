package io.porko.budget.domain;

import io.porko.member.domain.Member;
import io.porko.utils.ConvertUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@NoArgsConstructor
@Getter
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
