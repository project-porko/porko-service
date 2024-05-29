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
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepo historyRepo;
    private final MemberQueryRepo memberQueryRepo;

    public List<HistoryResponse> getHistoryList(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return getHistoryListByDate(loginMemberId, startDate, endDate);
        } else {
            return getAllHistoryList(loginMemberId);
        }
    }

    private List<HistoryResponse> getHistoryListByDate(Long loginMemberId, LocalDate startDate, LocalDate endDate) {
        loadMemberById(loginMemberId); // 회원 검증을 위한 호출
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

    private List<HistoryResponse> getAllHistoryList(Long loginMemberId) {
        loadMemberById(loginMemberId); // 회원 검증을 위한 호출

        List<History> histories = historyRepo.findByMemberId(loginMemberId);

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