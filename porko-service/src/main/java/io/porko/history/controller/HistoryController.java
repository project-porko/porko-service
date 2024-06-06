package io.porko.history.controller;

import io.porko.auth.controller.model.LoginMember;
import io.porko.history.controller.model.HistoryDetailResponse;
import io.porko.history.controller.model.HistoryListResponse;
import io.porko.history.controller.model.HistoryResponse;
import io.porko.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping
    ResponseEntity <HistoryListResponse> getHistoryList(
            @LoginMember Long loginMemberId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate == null || endDate == null) {
            return ResponseEntity.ok(historyService.getThisMonthHistoryList(loginMemberId));
        } else return ResponseEntity.ok(historyService.getHistoryListByDate(loginMemberId, startDate, endDate));
    }

    @GetMapping("/{historyId}")
    ResponseEntity<HistoryDetailResponse> getHistoryDetail(
            @PathVariable("historyId") Long historyId) {
        return ResponseEntity.ok(historyService.getHistoryDetail(historyId));
    }

    @PatchMapping("/{historyId}/regret")
    ResponseEntity<HistoryResponse> updateRegretStatus(
            @PathVariable("historyId") Long historyId,
            @RequestParam("regret") Boolean regret) {
        return ResponseEntity.ok(historyService.updateRegretStatus(historyId, regret));
    }
}
