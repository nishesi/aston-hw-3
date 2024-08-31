package ru.astondevs.attractionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.attractionservice.dao.SettlementRepository;
import ru.astondevs.attractionservice.dto.settlement.NewSettlementForm;
import ru.astondevs.attractionservice.dto.settlement.SettlementDto;
import ru.astondevs.attractionservice.dto.settlement.UpdateSettlementForm;
import ru.astondevs.attractionservice.exception.ServiceViolationException;
import ru.astondevs.attractionservice.mapper.SettlementMapper;
import ru.astondevs.attractionservice.model.Attraction;
import ru.astondevs.attractionservice.model.Settlement;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {
    private final SettlementRepository settlementRepository;
    private final SettlementMapper settlementMapper;

    @Transactional
    @Override
    public SettlementDto createSettlement(NewSettlementForm form) {
        log.debug("start createSettlement() with: {}", form);

        Settlement settlement = settlementMapper.toSettlement(form);
        settlement = settlementRepository.save(settlement);
        SettlementDto dto = settlementMapper.toSettlementDto(settlement, Set.of());

        log.debug("finish createSettlement() with: {}", dto);
        return dto;
    }

    @Transactional
    @Override
    public SettlementDto updateSettlement(Long settlementId, UpdateSettlementForm form) {
        log.debug("start updateSettlement() with: {}, {}", settlementId, form);

        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(() -> new ServiceViolationException(404, "Settlement not found"));
        Set<Long> attractionIds = settlement.getAttractions().stream()
                .map(Attraction::getId)
                .collect(Collectors.toSet());

        settlement.setPopulation(form.population());
        settlement.setHasSubway(form.hasSubway());
        settlement = settlementRepository.save(settlement);
        SettlementDto dto = settlementMapper.toSettlementDto(settlement, attractionIds);

        log.debug("finish updateSettlement() with: {}", dto);
        return dto;
    }
}
