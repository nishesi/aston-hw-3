package ru.astondevs.attractionservice.exception;


import lombok.Getter;

@Getter
public class ServiceViolationException extends RuntimeException {
    private final int code;

    public ServiceViolationException(int code, String message) {
        super(message);
        this.code = code;
    }
}
