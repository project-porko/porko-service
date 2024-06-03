package io.porko.history.controller;

import io.porko.auth.controller.model.LoginMember;
import io.porko.history.controller.model.HistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.porko.history.service.HistoryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("history")
    ResponseEntity<List<HistoryResponse>> getHistoryList(
            @LoginMember Long loginMemberId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate == null || endDate == null) {
            return ResponseEntity.ok(historyService.getThisMonthHistoryList(loginMemberId));
        } else return ResponseEntity.ok(historyService.getHistoryListByDate(loginMemberId, startDate, endDate));
    }
}
