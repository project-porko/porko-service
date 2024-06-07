package io.porko.history.controller;

import io.porko.auth.controller.model.LoginMember;
import io.porko.history.controller.model.CalendarResponse;
import io.porko.history.controller.model.HistoryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.porko.history.service.HistoryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping
    ResponseEntity<List<HistoryResponse>> getHistoryList(
            @LoginMember Long loginMemberId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate == null || endDate == null) {
            return ResponseEntity.ok(historyService.getThisMonthHistoryList(loginMemberId));
        } else return ResponseEntity.ok(historyService.getHistoryListByDate(loginMemberId, startDate, endDate));
    }

    @GetMapping("/{historyId}")
    ResponseEntity<HistoryResponse> getHistoryDetail(
            @PathVariable("historyId") Long historyId) {
        return ResponseEntity.ok(historyService.getHistoryDetail(historyId));
    }

    @GetMapping("/calendar")
    ResponseEntity<List<CalendarResponse>> getCalendar(
            @Valid @RequestParam Integer year,
            @Valid @RequestParam @Min(1) @Max(12) Integer month,
            @RequestParam(required = false) Long memberId,
            @LoginMember Long id) {
        if (memberId == null) {
            memberId = id;
        }

        return ResponseEntity.ok(historyService.getCalendar(year, month, memberId));
    }
}
