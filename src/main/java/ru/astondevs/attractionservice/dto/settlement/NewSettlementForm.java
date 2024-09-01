package ru.astondevs.attractionservice.dto.settlement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record NewSettlementForm(@NotBlank @Size(max = 255)
                                String name,
                                @NotNull @Positive
                                Integer population,
                                @NotNull
                                Boolean hasSubway) {
}