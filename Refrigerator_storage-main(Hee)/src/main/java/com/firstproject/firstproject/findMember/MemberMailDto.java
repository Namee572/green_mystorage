package com.firstproject.firstproject.findMember;


import lombok.Builder;
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

    public void mailSend(JavaMailSender mailSender){
        System.out.println("이멜 전송 완료!");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message);

        mailSender.send(simpleMailMessage);
    }

}