package ru.astondevs.attractionservice.dto.attraction;

import jakarta.validation.constraints.*;
import ru.astondevs.attractionservice.model.Attraction;

import java.time.LocalDate;
import java.util.Set;

public record NewAttractionForm(@NotBlank @Size(max = 255)
                                String name,
                                @NotNull @Past
                                LocalDate creationDate,
                                @NotBlank @Size(max = 1000)
                                String description,
                                @NotNull
                                Attraction.Type type,
                                @NotNull @Positive
                                Long settlementId,
                                @NotNull
                                Set<Long> serviceIds
) {
}
