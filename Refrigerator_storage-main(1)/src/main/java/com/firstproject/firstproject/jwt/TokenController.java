package com.firstproject.firstproject.jwt;


import com.firstproject.firstproject.member.Member;
import com.firstproject.firstproject.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenManager tokenManager;
    private final MemberRepository memberRepository;


    //토큰 발행하는 구문
    @PostMapping("/token")
    public String token(@RequestBody TokenDto tokenDto){
        Member dbmember =

        return "token";
    }
}
