package com.firstproject.firstproject.member.exception;

import com.firstproject.firstproject.exception.BaseException;
import com.firstproject.firstproject.exception.BaseExceptionType;

public class MemberException extends BaseException {

    private BaseExceptionType exceptionType;


    public MemberException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
