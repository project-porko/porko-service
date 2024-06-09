package io.porko.history.domain;

import io.porko.account.domain.TransactionRecord;
import io.porko.member.domain.Member;
import java.util.List;
import java.util.stream.Collectors;

public record Histories(
    List<History> elements
) {
    public static Histories of(Member member, List<TransactionRecord> transactionRecords) {
        return new Histories(transactionRecords.stream()
            .map(it -> it.toHistory(member))
            .collect(Collectors.toList()));
    }
}
