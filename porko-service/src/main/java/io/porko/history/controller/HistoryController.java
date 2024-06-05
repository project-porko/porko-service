package io.porko.history.controller;

import io.porko.auth.controller.model.LoginMember;
import io.porko.history.controller.model.HistoryResponse;
import io.porko.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping
    ResponseEntity<Map<String, Object>> getHistoryList(
            @LoginMember Long loginMemberId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Map<String, Object> response;
        if (startDate == null || endDate == null) {
            response = historyService.getThisMonthHistoryList(loginMemberId);
        } else {
            response = historyService.getHistoryListByDate(loginMemberId, startDate, endDate);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{historyId}")
    ResponseEntity<HistoryResponse> getHistoryDetail(
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
