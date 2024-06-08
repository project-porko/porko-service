package io.porko.account.repo;

import io.porko.account.domain.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepo extends JpaRepository<AccountHolder, Long> {
}
