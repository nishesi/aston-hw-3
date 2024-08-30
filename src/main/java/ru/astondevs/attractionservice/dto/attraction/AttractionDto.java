package ru.astondevs.attractionservice.dto.attraction;

import lombok.Builder;
import ru.astondevs.attractionservice.model.Attraction;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record AttractionDto(Long id,
                            String name,
                            LocalDate creationDate,
                            String description,
                            Attraction.Type type,
                            Long settlementId,
                            Set<Long> serviceIds) {
}
