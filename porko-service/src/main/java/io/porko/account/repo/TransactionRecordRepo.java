package io.porko.account.repo;

import io.porko.account.domain.TransactionRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecordRepo extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findByAccountHolder_PhoneNumber(String phoneNumber);
}
