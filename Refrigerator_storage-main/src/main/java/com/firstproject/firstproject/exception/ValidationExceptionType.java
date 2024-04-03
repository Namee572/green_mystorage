package com.firstproject.firstproject.exception;

import com.firstproject.firstproject.exception.BaseExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus
public enum ValidationExceptionType implements BaseExceptionType {

    MISSING_TITLE(900, HttpStatus.BAD_REQUEST, "제목이 비어있습니다."),
    INVALID_CONTENT(901, HttpStatus.BAD_REQUEST, "내용이 비어있습니다");


    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    ValidationExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public  int getErrorCode() {
        return this.errorCode;
    }
    @Override
    public  HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
    @Override
    public String getErrorMessage(){
        return this.errorMessage;
}}
