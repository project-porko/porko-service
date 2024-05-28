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
    @Min(value = 1, message = "월은 1보다 작을 수 없습니다.")
    @Max(value = 12, message = "월은 12보다 클 수 없습니다.")
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
