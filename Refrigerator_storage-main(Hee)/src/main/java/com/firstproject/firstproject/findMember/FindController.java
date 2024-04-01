package com.firstproject.firstproject.findMember;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Controller
public class FindController {

    private final FindService findService;
    private final MemberMailDto memberMailDto;
    private final SendMailService sendMailService;


    //아이디찾기
    @GetMapping("/findId")
    public ResponseEntity<String> findEmail(@RequestBody MemberFindIdDto memberFindIdDto) {
        String id = findService.findEmail(memberFindIdDto);
        if (id != null) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디를 찾지 못했습니다.");
        }
    }

    //일치여부를 확인
    @GetMapping("/findPw")
    public ResponseEntity<String> findPw(@RequestBody MemberFindPwDto memberFindPwDto) {
        if (!memberFindPwDto.isValid()) {
            return ResponseEntity.badRequest().body("유효하지 않은 요청입니다.");
        }
        if (findService.checkCredentialsMatch(memberFindPwDto.getName(), memberFindPwDto.getEmail(), memberFindPwDto.getBirth())) {
            String Pw = findService.findPassword(memberFindPwDto);
            return ResponseEntity.ok("비밀번호가 이메일로 전송되었습니다. 비밀번호:" + Pw);
        } else {
            sendMailService.sendEmailForPasswordReset(memberFindPwDto.getEmail());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입력한 정보와 일치하는 사용자가 없습니다. 이메일로 비밀번호 재설정 안내가 전송되었습니다.");
        }
    }


    //등록된 이메일로 임시비밀번호 발송
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody MemberMailDto memberMailDto) {
        try {
            sendMailService.mailSend(memberMailDto);
            return ResponseEntity.ok("이메일이 성공적으로 전송되었습니다");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송에 실패했습니다");
        }
    }
}
