package com.firstproject.firstproject.findMember;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindService {

    private final FindRepository findRepository;
    public boolean checkCredentialsMatch;
    private JavaMailSender javaMailSender;


    //아이디 찾기
    public String findEmail(MemberFindIdDto memberFindIdDto){
        //이메일 + 생년월일로 찾기
        String name = memberFindIdDto.getName();
        String birth = memberFindIdDto.getBirth();

        Optional<FindMember> memberOptional = findRepository.findByNameAndBirth(name,birth);
        //람다형식으로 변경 했을 때 회원을 찾지 못 하는 경우 null로 반환
        return memberOptional.map(FindMember::getBirth).orElse(null);
    }

    //비밀번호 찾기
    public  String findPassword(MemberFindPwDto memberFindPwDto){
        //이름 + 이메일 + 생년월일로 찾기
        String name = memberFindPwDto.getName();
        String email = memberFindPwDto.getEmail();
        String birth = memberFindPwDto.getBirth();

        Optional<FindMember> memberOptionals = findRepository.findByBirthAndNameAndEmail(name,email,birth);

        return memberOptionals.map(FindMember::getEmail).orElse(null);
    }

    public boolean checkCredentialsMatch(String name, String email, String birth) {
        //데이터 베이스에서 이메일 이름 생년월일이 일치하는 사용자를 조회합니다.
        Optional<FindMember> MemberOptional = findRepository.findByBirthAndNameAndEmail(name, email, birth);
        //사용자가 존재하면 true, 존재하지않으면 false를 반환
        return MemberOptional.isPresent();
    }
}
