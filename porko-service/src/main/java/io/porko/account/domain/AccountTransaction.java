package io.porko.account.domain;

public record AccountTransaction(
    AccountHolder accountHolder,
    String initialDataFileName
) {
}
