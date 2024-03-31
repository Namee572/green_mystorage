package com.firstproject.firstproject.jwt;


import com.firstproject.firstproject.member.Member;
import com.firstproject.firstproject.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
        Member dbmember = memberRepository.findByEmailAndPassword(tokenDto.getEmail(), tokenDto.getPassword());
        if (dbmember == null) {
            throw new RuntimeException("인증에러");
        }

        return tokenManager.createToken(dbmember);
    }


    @GetMapping("/vaild")
    public String vaild(HttpServletRequest request){
            String auth = request.getHeader("Authorization");
        return "vaild";
    }

}
