package com.firstproject.firstproject.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        ValidationExceptionType exceptionType = ex.getType();
        String errorMessage = exceptionType.getErrorMessage();
        return ResponseEntity.status(exceptionType.getHttpStatus()).body(errorMessage);
    }
}
