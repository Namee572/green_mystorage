package com.firstproject.firstproject.jwt;


import com.firstproject.firstproject.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Component
public class TokenManager {

    @Value("${first.jwt.secret}")
    private String mykey;

    // 토큰 발급해주는 함수
    public String createToken(Member member) {

        return Jwts.builder()
                .subject("firstToken")
                .claim("id", member.getId())
                .claim("email", member.getEmail())
                .claim("name", member.getName())
                .claim("birth", member.getBirth())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 6))
                .signWith(hmacShaKeyFor(mykey.getBytes()))
                .compact();

    }

    // 토큰 검증해주는 함수
    public Jws<Claims> validateToken(String token) {

        Jws<Claims> jws = Jwts.parser()//번역�����
                .setSigningKey(hmacShaKeyFor(mykey.getBytes()))//비��번호로..
                .build()
                .parseSignedClaims(token); // claim 들을 번역하고 ����션타입


     return jws;
    }
}
