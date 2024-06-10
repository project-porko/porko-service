package io.porko.budget.controller;

import io.porko.auth.controller.model.LoginMember;
import io.porko.budget.controller.model.BudgetRequest;
import io.porko.budget.controller.model.BudgetResponse;
import io.porko.budget.controller.model.ManageBudgetResponse;
import io.porko.budget.service.BudgetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    ResponseEntity<Void> setBudget (@RequestBody BudgetRequest budgetRequest, @LoginMember Long id) {
        budgetService.setBudget(budgetRequest, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/lastused")
    ResponseEntity<BudgetResponse> getUsedCostInLastMonth(@LoginMember Long id) {
        return ResponseEntity.ok(budgetService.getUsedCostInLastMonth(id));
    }

    @GetMapping("/management")
    ResponseEntity<ManageBudgetResponse> manageBudget (@LoginMember Long id) {
        return ResponseEntity.ok(budgetService.manageBudget(id));
    }

    @PatchMapping
    ResponseEntity<Void> updateBudget (@RequestBody BudgetRequest budgetRequest, @LoginMember Long id) {
        budgetService.updateBudget(budgetRequest, id);
        return ResponseEntity.ok().build();
    }
}