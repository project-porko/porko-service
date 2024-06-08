package io.porko.account.repo;

import io.porko.account.domain.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecordRepo extends JpaRepository<TransactionRecord, Long> {
}
