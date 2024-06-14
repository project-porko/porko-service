package io.porko.account.event;

import io.porko.account.domain.AccountTransactions;

public record InitializeTransactionDataEvent(
    AccountTransactions accountHolders
) {
    public static InitializeTransactionDataEvent from(AccountTransactions accountHolders) {
        return new InitializeTransactionDataEvent(accountHolders);
    }
}
