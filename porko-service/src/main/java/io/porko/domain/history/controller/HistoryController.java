package io.porko.domain.history.controller;

import io.porko.domain.auth.controller.model.LoginMember;
import io.porko.domain.history.controller.model.CalendarResponse;
import io.porko.domain.history.controller.model.HistoryDetailResponse;
import io.porko.domain.history.controller.model.HistoryListResponse;
import io.porko.domain.history.controller.model.HistoryResponse;
import io.porko.domain.history.domain.History;
import io.porko.domain.history.service.HistoryService;
import io.porko.pagination.request.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping
    ResponseEntity<HistoryListResponse<History, HistoryResponse>> getHistoryList(
        @LoginMember Long loginMemberId,
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @Pagination
        @SortDefault(sort ="usedAt", direction = Sort.Direction.DESC)
        Pageable pagination
    ) {
        if (startDate == null || endDate == null) {
            return ResponseEntity.ok(historyService.getThisMonthHistoryList(loginMemberId, pagination));
        }
        return ResponseEntity.ok(historyService.getHistoryListByDate(loginMemberId, startDate, endDate, pagination));
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

    @GetMapping("/calendar")
    ResponseEntity<List<CalendarResponse>> getCalendar(
        @Valid @RequestParam int year,
        @Valid @RequestParam @Min(1) @Max(12) int month,
        @RequestParam(required = false) Long memberId,
        @LoginMember Long id) {
        if (memberId == null) {
            memberId = id;
        }

        return ResponseEntity.ok(historyService.getCalendar(year, month, memberId));
    }
}
