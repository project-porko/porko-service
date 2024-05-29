package io.porko.history.service;

import io.porko.history.controller.model.HistoryResponse;
import io.porko.history.domain.History;
import io.porko.history.repo.HistoryRepo;
import io.porko.member.repo.MemberQueryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepo historyRepo;
    private final MemberQueryRepo memberQueryRepo;

    public List<HistoryResponse> getThisMonthHistoryList(Long loginMemberId) {
        LocalDate now = LocalDate.now();
        YearMonth thisMonth = YearMonth.from(now);
        LocalDate startDate = thisMonth.atDay(1);
        LocalDate endDate = thisMonth.atEndOfMonth();
        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    public List<HistoryResponse> getHistoryListByDate(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    private List<HistoryResponse> fetchHistoryList(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        loadMemberById(loginMemberId);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<History> histories = historyRepo.findByMemberIdAndUsedAtBetween(loginMemberId, startDateTime, endDateTime);

        return histories.stream()
                .map(history -> new HistoryResponse(
                        history.getType(),
                        history.getUsedAt(),
                        history.getCost(),
                        history.getPlace(),
                        history.getSpendingCategoryId(),
                        history.getPayType()
                ))
                .collect(Collectors.toList());
    }

    private void loadMemberById(Long id) {
        memberQueryRepo.findMemberById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }
}
