package io.porko.domain.history.domain;

import io.porko.domain.account.domain.TransactionRecord;
import io.porko.domain.member.domain.Member;
import java.util.List;

public record Histories(
    List<History> elements
) {
    public static Histories of(Member member, List<TransactionRecord> transactionRecords) {
        return new Histories(transactionRecords.stream()
            .map(it -> it.toHistory(member))
            .toList());
    }
}
