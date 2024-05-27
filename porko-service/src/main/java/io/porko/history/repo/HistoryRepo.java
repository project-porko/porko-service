package io.porko.history.repo;

import io.porko.history.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepo extends JpaRepository<History, Long> {

}
