package ru.astondevs.attractionservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.astondevs.attractionservice.dto.ExceptionDto;
import ru.astondevs.attractionservice.dto.ValidationErrorDto;
import ru.astondevs.attractionservice.exception.ServiceViolationException;

import java.util.List;

/**
 * Обработчик исключений, которые могут выброситься во время выполнения запроса.
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ServiceViolationException.class)
    public ResponseEntity<ExceptionDto> handle(ServiceViolationException ex) {
        log.debug("exception handled: ", ex);
        return ResponseEntity.status(ex.getCode())
                .body(new ExceptionDto(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDto>> handle(MethodArgumentNotValidException ex) {
        log.debug("exception handled: ", ex);
        List<ValidationErrorDto> errors = ex.getAllErrors().stream()
                .map(error -> error instanceof FieldError field
                        ? new ValidationErrorDto(field.getField(), field.getDefaultMessage(), field.getRejectedValue())
                        : new ValidationErrorDto(error.getObjectName(), error.getDefaultMessage(), null))
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handle(Exception e) {
        log.error("unhandled exception", e);
        return ResponseEntity.internalServerError()
                .body(new ExceptionDto(500, "Internal Server Error"));
    }
}
