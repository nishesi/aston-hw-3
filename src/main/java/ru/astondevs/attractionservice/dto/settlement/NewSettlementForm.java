package ru.astondevs.attractionservice.dto.settlement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NewSettlementForm(@NotBlank
                                String name,
                                @NotNull @Positive
                                Integer population,
                                @NotNull
                                Boolean hasSubway) {
}