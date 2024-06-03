package io.porko.history.repo;

import io.porko.history.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HistoryRepo extends JpaRepository<History, Long> {
    List<History> findByMemberIdAndUsedAtBetween(
            Long memberId,
            LocalDateTime startDate,
            LocalDateTime endDate);

    @Query("SELECT SUM(h.cost) FROM History h WHERE h.member.id = :memberId AND h.type = 'SPENT' AND h.usedAt BETWEEN :startDateTime AND :endDateTime")
    Optional<BigDecimal> calcUsedCostForPeriod(@Param("memberId") Long memberId, @Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT SUM(h.cost) FROM History h WHERE h.member.id = :memberId AND h.type = 'EARNED' AND h.usedAt BETWEEN :startDateTime AND :endDateTime")
    Optional<BigDecimal> calcEarnedCostForPeriod(@Param("memberId") Long memberId, @Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
}
