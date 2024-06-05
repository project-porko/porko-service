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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepo historyRepo;
    private final MemberQueryRepo memberQueryRepo;

    public Map<String, Object> getThisMonthHistoryList(Long loginMemberId) {
        YearMonth thisMonth = YearMonth.now();
        LocalDate startDate = thisMonth.atDay(1);
        LocalDate endDate = thisMonth.atEndOfMonth();

        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    public Map<String, Object> getHistoryListByDate(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        return fetchHistoryList(loginMemberId, startDate, endDate);
    }

    public HistoryResponse getHistoryDetail(Long historyId) {
        History history = historyRepo.findById(historyId)
                .orElseThrow(() -> new RuntimeException("상세조회를 찾을 수 없습니다: " + historyId));
        return HistoryResponse.ofDetail(history);
    }

    @Transactional
    public HistoryResponse updateRegretStatus(Long historyId, Boolean regret) {
        History history = historyRepo.findById(historyId)
                .orElseThrow(() -> new RuntimeException("상세조회를 찾을 수 없습니다: " + historyId));
        history.updateRegret(regret);
        historyRepo.save(history);
        return HistoryResponse.ofDetail(history);
    }

    public Map<String, Object> fetchHistoryList(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        loadMemberById(loginMemberId);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        BigDecimal totalSpent = historyRepo.calcSpentCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);
        BigDecimal totalEarned = historyRepo.calcEarnedCostForPeriod(loginMemberId, startDateTime, endDateTime).orElse(BigDecimal.ZERO);

        List<History> histories = historyRepo.findByMemberIdAndUsedAtBetween(loginMemberId, startDateTime, endDateTime);

        List<HistoryResponse> historyResponses = histories.stream()
                .map(HistoryResponse::of)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("historyList", historyResponses);
        response.put("totalSpent", totalSpent);
        response.put("totalEarned", totalEarned);
        return response;
    }

    private void loadMemberById(Long id) {
        memberQueryRepo.findMemberById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }
}
