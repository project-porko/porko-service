package io.porko.budget.domain;

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

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private BigDecimal goalCost;

    @Column(nullable = false)
    private Integer goalYear;

    @Column(nullable = false)
    private Integer goalMonth;

    private Budget(
            Long memberId,
            BigDecimal goalCost,
            Integer goalYear,
            Integer goalMonth
    ) {
        this.memberId = memberId;
        this.goalCost = goalCost;
        this.goalYear = goalYear;
        this.goalMonth = goalMonth;
    }

    public static Budget of(
            Long memberId,
            BigDecimal goalCost,
            Integer goalYear,
            Integer goalMonth
    ) {
        return new Budget(memberId, goalCost, goalYear, goalMonth);
    }
}
