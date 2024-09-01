package ru.astondevs.attractionservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.attractionservice.dto.settlement.NewSettlementForm;
import ru.astondevs.attractionservice.dto.settlement.SettlementDto;
import ru.astondevs.attractionservice.dto.settlement.UpdateSettlementForm;
import ru.astondevs.attractionservice.service.SettlementService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class SettlementRestController {
    private final SettlementService settlementService;

    @PostMapping("/v1/settlement")
    ResponseEntity<SettlementDto> createSettlement(@RequestBody @Valid NewSettlementForm form) {
        SettlementDto dto = settlementService.createSettlement(form);
        return ResponseEntity.status(CREATED).body(dto);
    }

    @PutMapping("/v1/settlement/{settlement-id}")
    ResponseEntity<SettlementDto> updateSettlement(@PathVariable("settlement-id") Long settlementId,
                                                    @RequestBody @Valid UpdateSettlementForm form) {
        SettlementDto dto = settlementService.updateSettlement(settlementId, form);
        return ResponseEntity.ok(dto);
    }
}
