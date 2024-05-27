package io.porko.budget.domain;

import io.porko.utils.ConvertUtils;
import jakarta.persistence.*;
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
    private YearMonth goalDate;

    private Budget(
            Long memberId,
            BigDecimal goalCost,
            YearMonth goalDate
    ) {
        this.memberId = memberId;
        this.goalCost = goalCost;
        this.goalDate = goalDate;
    }

    public static Budget of(
            Long memberId,
            BigDecimal goalCost,
            YearMonth goalDate
    ) {
        return new Budget(memberId, goalCost, goalDate);
    }
}
