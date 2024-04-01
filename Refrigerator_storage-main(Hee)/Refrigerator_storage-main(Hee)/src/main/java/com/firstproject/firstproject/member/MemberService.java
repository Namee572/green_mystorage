package com.firstproject.firstproject.member;


import com.firstproject.firstproject.member.exception.MemberException;
import com.firstproject.firstproject.member.exception.MemberExceptionType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private  final MemberRepository memberRepository;
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
        String name = memberFindDto.getName();
        String birth = memberFindDto.getBirth();

        Optional<Member> memberOptional = memberRepository.findByNameAndBirth(name, birth);

        return memberOptional.map(Member::getBirth).orElse(null);
    }

    /*최신 비밀번호*/
    public String findLastPw(MemberFindPwDto memberFindDto) {
        // 이름 + 이메일 + 생년월일로 찾기
        String name = memberFindDto.getName();
        String email = memberFindDto.getEmail();
        String birth = memberFindDto.getBirth();

        Optional<Member> memberOptional = memberRepository.findByBirthAndNameAndEmail(birth, name, email);

        return memberOptional.map(Member::getEmail).orElse(null);
    }

    /*일치 여부 확인*/
    public boolean checkCredentialsMatch(String name, String email, String birth) {
        //데이터 베이스에서 이메일 이름 생년월일 일치하는 사용자 조회
        Optional<Member> memberOptional = memberRepository.findByBirthAndNameAndEmail(name, email, birth);
        //사용자가 존재하면 true, 존재하지않으면 false 반환
        return memberOptional.isPresent();
    }

    private static final String FROM_ADDRESS = "본인의 이메일 주소를 입력해달라!";

    /*메일전송 메소드 -> 발신자 수신자 제목 본문을 입력으로 받아서 이메일 보냄*/
    private void sendEmail(String sender, String receiver, String title, String message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF_8");

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(message, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일을 보내지 못 했습니다.", e);
        }
    }
    /*메일 전송 메소드 호출 memberDto 객체를 입력으로 받아서 발신자 수신자 제목 본문을 추출
    * sendEmail에 메소드 전달*/
    public void mailSend(MemberMailDto memberMailDto) {
        String sender = memberMailDto.getSender();
        String receiver = memberMailDto.getReceiver();
        String title = memberMailDto.getTitle();
        String message = memberMailDto.getMessage();

        sendEmail(sender, receiver, title, message);
    }


    /*최신 변경 된 비밀 번호를 찾고 없으면 이메일 전송 최신 변경 된 패스워드*/
    public void lastPassword(MemberMailDto member) {
        String lastPw = String.valueOf(memberRepository.findLatestPasswordForMember()
                .orElse(null));

        if (lastPw != null){
            String subject = "최신 변경된 패스워드 확인";
            String message = "최신 변경된 패스워드: " + lastPw;
            mailSend(new MemberMailDto());
        }else {
            //최신 변경된 패스워드가 없다면 반응형으로 메시지 전송
             String subject = "패스워드 확인 요청";
             String message = "최신 변경된 패스워드가 없습니다. 패스워드를 확인해주세요.";
             mailSend(new MemberMailDto());
        }
    }

    public String findLastPassword(
            MemberFindPwDto memberFindPwDto
    ) {
//    public String findLastPassword(
//            String name, String email,String birth
//    ) {

        String name = memberFindPwDto.getName();
        String email = memberFindPwDto.getEmail();
        String birth = memberFindPwDto.getBirth();

        String findLastPassword = String.valueOf(memberRepository.findLatestPasswordForMember());

        if (findLastPassword != null){
            //최신 변경된 비밀번호를 찾은 경우
            return "최신 변경된 비밀번호 :" + findLastPassword;
        }else {
            //최신 변경된 비밀번호가 없는 경우
            String temporaryPassword = generateTempPassword();
            String emailSubject = "임시 비밀번호 안내";
            String emailMessage = "안녕하세요." + name + "님!\n 회원님의 임시 비밀번호는 " + temporaryPassword + "입니다. 보안을 위해 로그인 후 비밀번호 변경을 해주세요.";

            mailSend(sendEmail);

            return "임시 비밀번호를 이메일로 전송 하였습니다.";
        }
    }
    /*임시 비밀번호를 생성하고 해당 비밀번호 포함 회원 메일 보내기*/
    private String generateTempPassword(){
        StringBuilder tempPassword = new StringBuilder();
        Random random = new Random();
        String CHARACTER_SET =  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$!%*#?&";
        for(int i =0;i < 12; i++){
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
