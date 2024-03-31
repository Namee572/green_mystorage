package com.firstproject.firstproject.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    @Value("${first.jwt.secret}")
    private String mykey;

    // 토큰 발급해주는 함수
    public String createToken() {

        return Jwts.builder()

                .build();


    }
}
