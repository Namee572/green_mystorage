package com.firstproject.firstproject.conf;

import com.firstproject.firstproject.jwt.TokenManager;
import com.firstproject.firstproject.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WebFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("필터동작");

        String token = request.getHeader("Authorization");
        System.out.println("token: " + token);

        String url = request.getRequestURI();

        if (url.equals("/token") || url.equals("/join")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (token == null || !token.startsWith("Bearer ")) {

            throw new RuntimeException("JWT 토큰 발행후에 해라");
        }




        try {
            System.out.println("여기오니");

            Jws<Claims> jws = tokenManager.validateToken(token.substring("Bearer ".length()));
            System.out.println(jws);

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            System.out.println(authorities);

            //로그인 한 정보를 Authentication 에 저장한다.
            Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                    Member.builder()
                            .email(jws.getPayload().get("email").toString())
                            .birth(jws.getPayload().get("birth").toString())
                            .id(jws.getPayload().get("id", Long.class))
                            .name(jws.getPayload().get("name").toString())
                            .build(),
                    null,
                    authorities

            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {

            throw new RuntimeException("토큰 만료되었습니다.");
        } catch (Exception e) {

            throw new RuntimeException("토큰이 이상하다.");
        }


    }
}
