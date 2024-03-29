package com.firstproject.firstproject.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleBaseEx(BaseException exception){
        return new ResponseEntity(new ExceptionDto(exception.getExceptionType().getErrorCode(),"BaseException"),
                exception.getExceptionType().getHttpStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleValidEx(BindException exception){
        List<String> list =
                exception.getFieldErrors().stream().map(e->e.getCode()+e.getDefaultMessage())
                        .toList();

        return new ResponseEntity(new ExceptionDto(2000,"유효성검사 실패"+list), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableExceptionEx(HttpMessageNotReadableException exception){

        return new ResponseEntity(new ExceptionDto(3000,"JSON 파싱오류"),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleMemberEx(Exception exception) {

        exception.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    static class ExceptionDto {
        private Integer errorCode;
        private String message;
    }

}
