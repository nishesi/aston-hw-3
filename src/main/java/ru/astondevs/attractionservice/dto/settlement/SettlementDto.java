package ru.astondevs.attractionservice.dto.settlement;

import lombok.Builder;

import java.util.Set;

/**
 * DTO for {@link ru.astondevs.attractionservice.model.Settlement}
 */
@Builder
public record SettlementDto(Long id,
                            String name,
                            int population,
                            boolean hasSubway,
                            Set<Long> attractionIds) {
}