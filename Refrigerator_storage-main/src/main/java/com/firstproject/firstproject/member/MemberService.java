package com.firstproject.firstproject.member;


import com.firstproject.firstproject.exception.MemberException;
import com.firstproject.firstproject.exception.MemberExceptionType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
public class MemberService {

    private final MemberRepository memberRepository;
    private String loggedUser;
    private final JavaMailSender javaMailSender;
    private MemberMailDto sendEmail;

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

    public char[] updatePassword(String isPassword, String toBePassword, String checkPassword, char[] asIsPassword) {

        Member member = memberRepository.findByEmail(loggedUser)
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        if (!member.matchPassword(String.valueOf(asIsPassword))) {
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        } else if (!toBePassword.equals(checkPassword)) {
            throw new MemberException(MemberExceptionType.PASSWORD_MISMATCH);
        }

        member.updatePassword(toBePassword);

        memberRepository.save(member);

        return asIsPassword;

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

    /*아이디 찾기*/
    public String findEmail(MemberFindIdDto memberFindDto) {
        //이메일 + 생년월일로 찾기
        Optional<Member> memberOptional = memberRepository.findByNameAndBirth(memberFindDto.getName(), memberFindDto.getBirth());

        return memberOptional.map(Member::getEmail).orElse(null);
    }

    /*최신 비밀번호*/
    public String findLastPw(MemberFindPwDto memberFindDto) {
        // 이름 + 이메일 + 생년월일로 찾기
        Optional<Member> memberOptional = memberRepository.findByBirthAndNameAndEmail(memberFindDto.getBirth(), memberFindDto.getName(), memberFindDto.getEmail());
        mailSend(memberFindDto);
        return memberOptional.map(Member::getPassword).orElse("찾을 수 없습니다.");
    }

    /*일치 여부 확인*/
    public boolean checkCredentialsMatch(MemberFindPwDto memberFindPwDto) {
        //데이터 베이스에서 이메일 이름 생년월일 일치하는 사용자 조회
        Optional<Member> memberOptional = memberRepository.findByBirthAndNameAndEmail(memberFindPwDto.getBirth(), memberFindPwDto.getName(), memberFindPwDto.getEmail());
        //사용자가 존재하면 true, 존재하지않으면 false 반환
        return memberOptional.isPresent();
    }

    /*메일 전송 메소드 호출 memberDto 객체를 입력으로 받아서 발신자 수신자 제목 본문을 추출
     * sendEmail에 메소드 전달*/
    public void mailSend(MemberFindPwDto memberFindPwDto) {

        String sender = memberFindPwDto.getName();
        String receiver = memberFindPwDto.getName();

        sendEmail("beauty1746@gmail.com", "beauty1746@gmail.com", "놈팽이 입니다.", " 안녕하세요:)  새로운 비빌번호는 " + generateTempPassword() + " 입니다. 보안을 위해 바로 변경 부탁드립니다.");
    }

    /*메일전송 메소드 -> 발신자 수신자 제목 본문을 입력으로 받아서 이메일 보냄*/
    private void sendEmail(String sender, String receiver, String title, String message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        System.out.println(sender);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF8");

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(message, true);


            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("이메일을 보내지 못 했습니다.", e);
        }
    }

    /*임시 비밀번호를 생성하고 해당 비밀번호 포함 회원 메일 보내기*/
    private String generateTempPassword() {
        StringBuilder tempPassword = new StringBuilder();
        Random random = new Random();
        String CHARACTER_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$!%*#?&";
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(CHARACTER_SET.length());
            tempPassword.append(CHARACTER_SET.charAt(index));
        }
        return tempPassword.toString();
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
