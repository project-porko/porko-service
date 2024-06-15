package io.porko.domain.account.domain;

import java.util.List;

public record AccountTransactions(
    List<AccountTransaction> elements
) {
    public List<AccountHolder> getHolders() {
        return elements.stream()
            .map(AccountTransaction::accountHolder)
            .toList();
    }
}
