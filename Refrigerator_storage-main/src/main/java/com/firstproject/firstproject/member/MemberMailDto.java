package com.firstproject.firstproject.member;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class MemberMailDto {
    //발송할 이메일 정보 저장할 Dto
    private String sender;
    private String receiver;
    private String title;
    private String message;
    private JavaMailSender mailSender;
    private String email;

    private String password;


}