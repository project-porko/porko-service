package io.porko.domain.consumption.controller;

import io.porko.domain.auth.controller.model.LoginMember;
import io.porko.domain.consumption.controller.model.RegretResponse;
import io.porko.domain.consumption.controller.model.WeatherResponse;
import io.porko.domain.consumption.service.ConsumptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumption")
@RequiredArgsConstructor
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @GetMapping("/regret")
    public ResponseEntity<RegretResponse> getRegretItem(@Valid @RequestParam int year,
                                                        @Valid @RequestParam @Min(1) @Max(12) int month,
                                                        @RequestParam(required = false) Long memberId,
                                                        @LoginMember Long id) {
        if (memberId == null) {
            memberId = id;
        }

        return ResponseEntity.ok(consumptionService.makeRegretResponse(year, month, memberId));
    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherResponse> countWeather(@Valid @RequestParam int year,
                                                        @Valid @RequestParam @Min(1) @Max(12) int month,
                                                        @RequestParam(required = false) Long memberId,
                                                        @LoginMember Long id) {
        if (memberId == null) {
            memberId = id;
        }

        return ResponseEntity.ok(consumptionService.makeWeatherResponse(year, month, memberId));
    }
}
