package ru.astondevs.attractionservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.astondevs.attractionservice.dto.settlement.NewSettlementForm;
import ru.astondevs.attractionservice.dto.settlement.SettlementDto;
import ru.astondevs.attractionservice.dto.settlement.UpdateSettlementForm;
import ru.astondevs.attractionservice.service.SettlementService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/settlement")
@RequiredArgsConstructor
public class SettlementRestController {
    private final SettlementService settlementService;

    @PostMapping
    ResponseEntity<SettlementDto> createSettlement(@RequestBody @Valid NewSettlementForm form) {
        SettlementDto dto = settlementService.createSettlement(form);
        return ResponseEntity.status(CREATED).body(dto);
    }

    @PutMapping("/{settlement-id}")
    ResponseEntity<SettlementDto> updateSettlement(@PathVariable("settlement-id") Long settlementId,
                                                    @RequestBody @Valid UpdateSettlementForm form) {
        SettlementDto dto = settlementService.updateSettlement(settlementId, form);
        return ResponseEntity.ok(dto);
    }
}
