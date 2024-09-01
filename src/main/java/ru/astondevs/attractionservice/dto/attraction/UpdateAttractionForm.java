package ru.astondevs.attractionservice.dto.attraction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAttractionForm(@NotBlank @Size(max = 255) String description) {
}
