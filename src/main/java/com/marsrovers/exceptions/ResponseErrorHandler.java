package com.marsrovers.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ResponseErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistsEntityException.class)
    public final ResponseEntity<ErrorDetails> handleEntityAlreadyExists(AlreadyExistsEntityException ex, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("AlreadyExistsEntityException", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), messages,
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("EntityNotFoundException", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), messages,
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("IllegalArgumentException", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), messages,
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {

        Map<String, String> messages = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            messages.put(fieldName, errorMessage);
        });

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), messages,
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
