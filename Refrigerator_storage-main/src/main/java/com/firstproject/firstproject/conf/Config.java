package com.firstproject.firstproject.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

//스프링 프레임워크에서 이메일을 보내기위한 설정
@Configuration
public class Config {

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender  = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("beauty1746@gmail.com");
        javaMailSender.setPassword("nclt duxy epxj uctj");

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");

        return javaMailSender;
    }




}

