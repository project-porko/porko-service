package io.porko.history.repo;

import io.porko.history.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryRepo extends JpaRepository<History, Long> {
    List<History> findByMemberIdAndUsedAtBetween(
            Long memberId,
            LocalDateTime startDate,
            LocalDateTime endDate);
}
