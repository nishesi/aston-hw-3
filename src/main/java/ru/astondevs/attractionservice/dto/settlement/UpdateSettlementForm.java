package ru.astondevs.attractionservice.dto.settlement;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateSettlementForm(@NotNull @Positive Integer population,
                                   @NotNull Boolean hasSubway) {
}
