package io.porko.domain.account.repo;

import io.porko.domain.account.domain.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepo extends JpaRepository<AccountHolder, Long> {
}
