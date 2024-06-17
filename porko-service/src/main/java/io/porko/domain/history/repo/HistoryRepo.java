package io.porko.domain.history.repo;

import io.porko.domain.history.domain.History;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HistoryRepo extends JpaRepository<History, Long> {
    Page<History> findByMemberIdAndUsedAtBetween(
        Long memberId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Pageable pageable
    );

    @Query("SELECT SUM(h.cost) FROM History h WHERE h.member.id = :memberId AND h.cost < 0 AND h.usedAt BETWEEN :startDateTime AND :endDateTime")
    Optional<BigDecimal> calcSpentCostForPeriod(@Param("memberId") Long memberId, @Param("startDateTime") LocalDateTime startDateTime,
        @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT SUM(h.cost) FROM History h WHERE h.member.id = :memberId AND h.cost > 0 AND h.usedAt BETWEEN :startDateTime AND :endDateTime")
    Optional<BigDecimal> calcEarnedCostForPeriod(@Param("memberId") Long memberId, @Param("startDateTime") LocalDateTime startDateTime,
        @Param("endDateTime") LocalDateTime endDateTime);
}
