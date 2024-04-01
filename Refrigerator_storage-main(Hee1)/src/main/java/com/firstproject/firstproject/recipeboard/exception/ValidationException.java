package com.firstproject.firstproject.recipeboard.exception;

public class ValidationException extends RuntimeException {
    private final ValidationExceptionType type;

    public ValidationException(ValidationExceptionType type) {
        this.type = type;
    }

    public ValidationExceptionType getType() {
        return type;
    }
}
