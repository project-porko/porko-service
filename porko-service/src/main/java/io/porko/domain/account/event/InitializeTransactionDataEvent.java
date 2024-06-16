package io.porko.domain.account.event;

import io.porko.domain.account.domain.AccountTransactions;

public record InitializeTransactionDataEvent(
    AccountTransactions accountHolders
) {
    public static InitializeTransactionDataEvent from(AccountTransactions accountHolders) {
        return new InitializeTransactionDataEvent(accountHolders);
    }
}
