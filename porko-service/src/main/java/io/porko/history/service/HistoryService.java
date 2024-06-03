package io.porko.history.service;

import io.porko.history.controller.model.HistoryResponse;
import io.porko.history.domain.History;
import io.porko.history.repo.HistoryRepo;
import io.porko.member.repo.MemberQueryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        YearMonth thisMonth = YearMonth.now();
        LocalDate startDate = thisMonth.atDay(1);
        LocalDate endDate = thisMonth.atEndOfMonth();

        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    public List<HistoryResponse> getHistoryListByDate(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    public HistoryResponse getHistoryDetail( Long historyId) {
        History history = historyRepo.findById(historyId)
                .orElseThrow(() -> new RuntimeException("상세조회를 찾을수 없습니다." + historyId));
        return HistoryResponse.ofDetail(history);
    }

    private List<HistoryResponse> fetchHistoryList(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        loadMemberById(loginMemberId);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        BigDecimal totalSpent = historyRepo.calcUsedCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);
        BigDecimal totalEarned = historyRepo.calcEarnedCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);

        List<History> histories = historyRepo.findByMemberIdAndUsedAtBetween(loginMemberId, startDateTime, endDateTime);

        return histories.stream()
                .map(history -> HistoryResponse.of(history, totalSpent, totalEarned))
                .collect(Collectors.toList());
    }

    private void loadMemberById(Long id) {
        memberQueryRepo.findMemberById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }
}
