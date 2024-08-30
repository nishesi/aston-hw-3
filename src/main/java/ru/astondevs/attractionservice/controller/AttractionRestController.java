package ru.astondevs.attractionservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.attractionservice.dto.attraction.AttractionDto;
import ru.astondevs.attractionservice.dto.attraction.NewAttractionForm;
import ru.astondevs.attractionservice.dto.attraction.UpdateAttractionForm;
import ru.astondevs.attractionservice.model.Attraction;
import ru.astondevs.attractionservice.service.AttractionService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class AttractionRestController {

    private final AttractionService attractionService;

    @PostMapping("/v1/attraction")
    public ResponseEntity<AttractionDto> createAttraction(@RequestBody @Valid NewAttractionForm form) {
        AttractionDto dto = attractionService.createAttraction(form);
        return ResponseEntity.status(CREATED).body(dto);
    }

    @GetMapping("/v1/attraction")
    public ResponseEntity<Page<AttractionDto>> getAllAttractions(
        @RequestParam(name = "nameSort", required = false) Sort.Direction nameSort,
        @RequestParam(name = "type", required = false) Attraction.Type type,
        @PageableDefault Pageable pageable) {

        Page<AttractionDto> page = attractionService.getAllAttractions(nameSort, type, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/v1/settlement/{settlement-id}/attractions")
    ResponseEntity<List<AttractionDto>> getSettlementAttractions(@PathVariable("settlement-id") Long settlementId) {
        List<AttractionDto> list = attractionService.getSettlementAttractions(settlementId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/v1/attraction/{attraction-id}")
    ResponseEntity<AttractionDto> updateAttraction(@PathVariable("attraction-id") Long attractionId,
                                                   @RequestBody @Valid UpdateAttractionForm form) {
        AttractionDto attraction = attractionService.updateAttraction(attractionId, form);
        return ResponseEntity.ok(attraction);
    }

    @DeleteMapping("v1/attraction/{attraction-id}")
    ResponseEntity<Void> deleteAttraction(@PathVariable("attraction-id") Long attractionId) {
        attractionService.deleteAttraction(attractionId);
        return ResponseEntity.noContent().build();
    }
}
