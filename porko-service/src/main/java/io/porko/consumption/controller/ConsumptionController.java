package io.porko.consumption.controller;

import io.porko.auth.controller.model.LoginMember;
import io.porko.consumption.controller.model.RegretResponse;
import io.porko.consumption.service.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumption")
@RequiredArgsConstructor
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @GetMapping("/regret")
    public ResponseEntity<RegretResponse> getRegretItem(@LoginMember Long id) {
        return ResponseEntity.ok(consumptionService.makeRegretResponse(id));
    }
}
