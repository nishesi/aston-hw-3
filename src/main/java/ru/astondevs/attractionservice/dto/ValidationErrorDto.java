package ru.astondevs.attractionservice.dto;

public record ValidationErrorDto(String path, String message, Object value) {
}
