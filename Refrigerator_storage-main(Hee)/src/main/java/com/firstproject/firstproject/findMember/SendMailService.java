package com.firstproject.firstproject.findMember;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class SendMailService {

    private JavaMailSender javaMailSender;
    private  FindRepository findRepository;

    private static final String FROM_ADDRESS = " 본인의 이메일 주소를 입력해줘라!!";


    //메일 전송 메소드
    private void sendMail(String sender, String receiver, String title, String message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(message, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일을 보내지 못 했습니다.", e);
        }
    }

    //메일 전송 메소드 호출
    public void mailSend(MemberMailDto memberMailDto) {
        String sender = memberMailDto.getSender();
        String receiver = memberMailDto.getReceiver();
        String title = memberMailDto.getTitle();
        String message = memberMailDto.getMessage();

        sendMail(sender, receiver, title, message);
    }

    // 비밀번호 찾기 이메일 보내기
    @Transactional
    public FindMember sendPassword(FindMember member) {
        // 회원 정보 조회

        FindMember existingMember = findRepository.findByBirthAndNameAndEmail(member.getBirth(), member.getName(), member.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("회원님의 이메일을 찾을 수 없습니다. " + member.getEmail()));

        // 임시 비밀번호 생성
        String tempPassword = generateTempPassword();

        // 이메일 전송
        sendEmail(existingMember.getEmail(),
                "냉장고 홈페이지 임시 비밀번호 안내.",
                "안녕하세요, " + existingMember.getName() + "님.\n회원님의 임시 비밀번호는 "
                        + tempPassword + "입니다.");

        return existingMember;
    }

    // 비밀번호 찾기 이메일 보내기
    public void sendEmailForPasswordReset(String email) {
        // 이메일 제목과 내용 설정
        String subject = "비밀번호 설정 안내";
        String message = "비밀번호를 재설정하려면 다음 링크를 클릭하세요: <a href=\\\"http://example.com/reset-password\\\">비밀번호 재설정</a>";

        // 이메일 전송
        sendEmail(email, subject, message);
    }

    // 이메일 전송 메소드
    private void sendEmail(String receiver, String subject, String message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // MimeMessage 및 MimeMessageHelper 생성
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, false);

            // 이메일 발송
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // 이메일 전송 중 예외 발생 시 예외 처리
            throw new RuntimeException("이메일을 보내지 못 했습니다.", e);
        }
    }

    // 임시 비밀번호 생성 메소드
    private String generateTempPassword() {
        StringBuilder tempPassword = new StringBuilder();
        Random random = new Random();
        String CHARACTER_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(CHARACTER_SET.length());
            tempPassword.append(CHARACTER_SET.charAt(index));
        }
        return tempPassword.toString();
    }
}