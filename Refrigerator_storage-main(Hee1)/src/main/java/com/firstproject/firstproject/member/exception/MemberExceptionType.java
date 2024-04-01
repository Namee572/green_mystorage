package com.firstproject.firstproject.member.exception;

import com.firstproject.firstproject.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {

    ALREADY_EXIST_EMAIL(600, HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    ALREADY_EXIST_NICKNAME(601, HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    WRONG_PASSWORD(602, HttpStatus.BAD_REQUEST, "비밀번호가 잘못되었습니다."),
    PASSWORD_MISMATCH(603, HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_MEMBER(604, HttpStatus.NOT_FOUND, "회원 정보가 없습니다."),
    WITHDRAWN_MEMBER(605, HttpStatus.FORBIDDEN, "탈퇴한 회원입니다");


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

}
