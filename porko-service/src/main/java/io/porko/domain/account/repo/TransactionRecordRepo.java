package io.porko.domain.account.repo;

import io.porko.domain.account.domain.TransactionRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecordRepo extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findByAccountHolder_PhoneNumber(String phoneNumber);
}
