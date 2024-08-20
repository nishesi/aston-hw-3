package ru.astondevs.attractionservice.dto;

import lombok.Builder;

@Builder
public record ExceptionDto(int code, String message) {
}
