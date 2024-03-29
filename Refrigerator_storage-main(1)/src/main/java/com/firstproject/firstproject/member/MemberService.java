package com.firstproject.firstproject.member;

import com.firstproject.firstproject.member.exception.MemberException;
import com.firstproject.firstproject.member.exception.MemberExceptionType;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
public class MemberService {

    private final MemberRepository memberRepository;
    private String loggedUser;

    /* 회원가입 */

    public void signUp(MemberDto.SignUpDto signUpDto) {
        Member member = signUpDto.toEntity();

        member.setWithdrawn("N");

        if (memberRepository.findByEmail(signUpDto.email()).isPresent()) {
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_EMAIL);
        }
        if (memberRepository.findByNickName(signUpDto.nickName()).isPresent()) {
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_NICKNAME);
        }
        if (!signUpDto.password().equals(signUpDto.checkPassword())) {
            throw new MemberException(MemberExceptionType.PASSWORD_MISMATCH);
        }

        memberRepository.save(member);
    }

    /* 로그인 */

    public void login(MemberDto.LoginDto loginDto) {
        Optional<Member> memberOptional = memberRepository.findByEmail(loginDto.email());

        if (memberOptional.isEmpty()) {
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }

        Member member = memberOptional.get();

        if ("Y".equals(member.getWithdrawn())) {
            throw new MemberException(MemberExceptionType.WITHDRAWN_MEMBER);
        }
        if (!member.matchPassword(loginDto.password())) {
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        }
        this.loggedUser = loginDto.email();
    }

    /* 로그아웃 */

    public void logout() {
        this.loggedUser = null;
    }

    /* 회원정보 수정 */

    public void update(MemberDto.UpdateDto updateDto, String loggedUser) throws Exception {
        Member member = memberRepository.findByEmail(loggedUser)
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        updateDto.name().ifPresent(member::updateName);
        updateDto.nickName().ifPresent(member::updateNickName);

        memberRepository.save(member);

    }

    /* 비밀번호 수정 */

    public void updatePassword(String asIsPassword, String toBePassword, String checkPassword, String loggedUser) {

        Member member = memberRepository.findByEmail(loggedUser)
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        if (!member.matchPassword(asIsPassword)) {
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        }
        else if (!toBePassword.equals(checkPassword)) {
            throw new MemberException(MemberExceptionType.PASSWORD_MISMATCH);
        }

        member.updatePassword(toBePassword);

        memberRepository.save(member);

    }

    /* 회원탈퇴 */

    public void withdraw(String checkPassword, String loggedUser) {

        Member member = memberRepository.findByEmail(loggedUser)
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        if (!member.matchPassword(checkPassword)) {
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        }

        member.withdraw();

        memberRepository.save(member);

    }

    /* 내정보 조회 */

    public MemberInfoDto getMyInfo() {

        Member findMember = memberRepository.findByEmail(loggedUser)
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        return new MemberInfoDto(findMember);
    }

}




//  준재님코드
//@Transactional
//public Member update(@RequestParam String nickname , Member user) {
//    Member EmailUser = userRepository.findByEmailContainingAndUsernameContainingAndNumberContaining(user.getEmail(), user.getUsername(), user.getNumber());
//
//    if (EmailUser == null) {
//        System.out.println("emailUser is empty");
//        throw new MemberException(ErrorCode.NOTBLACKEAMIL);
//    }
//    //수정한 데이터를 입력
//    EmailUser.setNickname(nickname);
////        EmailUser.setPassword(user.getPassword());
////
////        System.out.println(EmailUser);
////
////        User dbuser = userRepository.save(EmailUser);
//
//    return EmailUser;
//}
//
//
//public List<Member> getusers(Member user) {
//    List<Member> list = userRepository.findAll();
//    return list;
//}
