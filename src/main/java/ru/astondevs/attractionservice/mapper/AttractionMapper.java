package ru.astondevs.attractionservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.astondevs.attractionservice.dto.attraction.AttractionDto;
import ru.astondevs.attractionservice.dto.attraction.NewAttractionForm;
import ru.astondevs.attractionservice.model.Attraction;
import ru.astondevs.attractionservice.model.Service;
import ru.astondevs.attractionservice.model.Settlement;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AttractionMapper {

    public Attraction toAttraction(NewAttractionForm form, Settlement settlement, Set<Service> services) {
        return Attraction.builder()
            .name(form.name())
            .creationDate(form.creationDate())
            .description(form.description())
            .settlement(settlement)
            .type(form.type())
            .services(services)
            .build();
    }

    public AttractionDto toAttractionDto(Attraction attraction, Set<Long> serviceIds) {
        return attractionDtoBuilder(attraction)
            .serviceIds(serviceIds)
            .build();
    }

    public AttractionDto toAttractionDto(Attraction attraction) {
        return attractionDtoBuilder(attraction).build();
    }

    private AttractionDto.AttractionDtoBuilder attractionDtoBuilder(Attraction attraction) {
        return AttractionDto.builder()
            .id(attraction.getId())
            .name(attraction.getName())
            .creationDate(attraction.getCreationDate())
            .description(attraction.getDescription())
            .settlementId(attraction.getSettlement().getId())
            .type(attraction.getType());
    }
}
