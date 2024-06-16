package io.porko.domain.account.domain;

public record AccountTransaction(
    AccountHolder accountHolder,
    String initialDataFileName
) {
}
