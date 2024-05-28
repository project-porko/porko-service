package io.porko.budget.controller;

import io.porko.auth.controller.model.LoginMember;
import io.porko.budget.controller.model.BudgetResponse;
import io.porko.budget.service.BudgetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/budget")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @GetMapping
    ResponseEntity<BudgetResponse> getBudget (@Valid @RequestParam Integer goalYear,
                                              @Valid @RequestParam @Min(1) @Max(12) Integer goalMonth,
                                              @RequestParam(required = false) Long memberId,
                                              @LoginMember Long id) {
        if (memberId == null) {
            memberId = id;
        }
        BudgetResponse budgetResponse = budgetService.getBudget(goalYear, goalMonth, memberId);

        return ResponseEntity.ok(budgetResponse);
    }
}