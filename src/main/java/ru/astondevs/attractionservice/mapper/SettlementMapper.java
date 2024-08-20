package ru.astondevs.attractionservice.mapper;

import org.springframework.stereotype.Component;
import ru.astondevs.attractionservice.dto.settlement.NewSettlementForm;
import ru.astondevs.attractionservice.dto.settlement.SettlementDto;
import ru.astondevs.attractionservice.model.Settlement;

import java.util.Set;

@Component
public class SettlementMapper {

    public Settlement toSettlement(NewSettlementForm form) {
        return Settlement.builder()
                .name(form.name())
                .population(form.population())
                .hasSubway(form.hasSubway())
                .build();
    }

    public SettlementDto toSettlementDto(Settlement settlement, Set<Long> attractionIds) {
        return SettlementDto.builder()
                .id(settlement.getId())
                .name(settlement.getName())
                .population(settlement.getPopulation())
                .attractionIds(attractionIds)
                .build();
    }
}
