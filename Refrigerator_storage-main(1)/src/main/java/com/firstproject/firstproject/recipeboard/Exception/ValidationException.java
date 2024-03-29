package com.firstproject.firstproject.recipeboard.Exception;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final ValidationExceptionType type;

    public ValidationException(ValidationExceptionType type) {
        this.type = type;
    }

    public ValidationExceptionType getType() {
        return type;
    }
}
