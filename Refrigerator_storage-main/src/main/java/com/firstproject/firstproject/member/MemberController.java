package com.firstproject.firstproject.member;


import com.firstproject.firstproject.exception.MemberException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "Member-Controller", description = "멤버 등록 로긴 수정 삭제 조회")
public class MemberController {

    private final MemberService memberService;

    /* 회원가입 */
    @PostMapping("/member/join")
    @Operation(summary = "회원가입")
    public ResponseEntity<String> signUp(@Valid @RequestBody MemberDto.SignUpDto signUpDto) {
        try {
            memberService.signUp(signUpDto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (MemberException e) {
            return ResponseEntity.status(e.getExceptionType().getHttpStatus())
                    .body(e.getExceptionType().getErrorMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }
    }


    /* 로그인 */

    @PostMapping("/member/login")
    @Operation(summary = "로그인")
    public ResponseEntity<String> login(@Valid @RequestBody MemberDto.LoginDto loginDto) {
        try {
            memberService.login(loginDto);
            return ResponseEntity.ok("로그인 성공");
        } catch (MemberException e) {
            return ResponseEntity.status(e.getExceptionType().getHttpStatus())
                    .body(e.getExceptionType().getErrorMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }
    }


    /* 로그아웃 */

    @PostMapping("/member/logout")
    @Operation(summary = "로그아웃")
    public String logout() {
        memberService.logout();
        return "redirect:/member/login";
    }


    /* 회원정보 수정 */

    @PutMapping("/member/update")
    @Operation(summary = "회원정보 수정")
    public ResponseEntity<String> update(@Valid @RequestBody MemberDto.UpdateDto updateDto) {
        try {
            memberService.update(updateDto, memberService.getLoggedUser());
            return ResponseEntity.ok("회원정보 업데이트 성공");
        } catch (MemberException e) {
            return ResponseEntity.status(e.getExceptionType().getHttpStatus())
                    .body(e.getExceptionType().getErrorMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }
    }


    /* 비밀번호 수정 */

    @PutMapping("/member/updatePw")
    @Operation(summary = "비밀번호 수정")
    public ResponseEntity<String> update(@Valid @RequestBody MemberDto.UpdatePasswordDto updatePasswordDto, String toBePassword, String checkPassword, char[] asIsPassword) {
        try {
            memberService.updatePassword(updatePasswordDto.asIsPassword(), toBePassword, checkPassword, asIsPassword);
            return ResponseEntity.ok("비밀번호 업데이트 성공");
        } catch (MemberException e) {
            return ResponseEntity.status(e.getExceptionType().getHttpStatus())
                    .body(e.getExceptionType().getErrorMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }
    }


    /* 회원탈퇴 */
    @PutMapping("/member/delete")
    @Operation(summary = "회원탈퇴")
    public ResponseEntity<String> withdraw(@Valid @RequestBody MemberDto.WithdrawDto withdrawDto) {
        try {
            memberService.withdraw(withdrawDto.checkPassword(), memberService.getLoggedUser());
            return ResponseEntity.ok("회원 탈퇴 성공");
        } catch (MemberException e) {
            return ResponseEntity.status(e.getExceptionType().getHttpStatus())
                    .body(e.getExceptionType().getErrorMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }
    }


    /* 내정보 조회 */
    @GetMapping("/member/myPage")
    @Operation(summary = "내정보 조회")
    public ResponseEntity getMyInfo(HttpServletResponse response) {

        MemberInfoDto memberInfoDto = memberService.getMyInfo();
        return new ResponseEntity(memberInfoDto, HttpStatus.OK);
    }

    //아이디 찾기
    @GetMapping("/findId")
    @Operation(summary = "아이디 찾기")
    public ResponseEntity<String> findEmail(@RequestBody MemberFindIdDto memberFindIdDto) {
        String id = memberService.findEmail(memberFindIdDto);
        if (id != null) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디를 찾지 못 했습니다.");
        }
    }

    //비밀번호찾기
    @PostMapping("/sendLastPw")
    @Operation(summary = "비밀번호 찾기")
    public ResponseEntity<String> findLastPw(@RequestBody MemberFindPwDto memberFindPwDto) {
        try {
            String email = memberFindPwDto.getEmail();
            String lastPw = memberService.findLastPw(memberFindPwDto);
            return ResponseEntity.ok("최신 등록 된 비밀번호는 " + lastPw);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("실패했습니다.");
        }
    }

    //최신 변경된 비밀번호와 일치하는게 없으면 이메일 전송
    @PostMapping("/findPw")
    @Operation(summary = "최신 변경된 비밀번호와 일치하는게 없으면 이메일 전송")
    public ResponseEntity<String> findPw(@RequestBody MemberFindPwDto memberFindPwDto) {
        if (memberService.checkCredentialsMatch(memberFindPwDto)) {
            String Pw = memberService.findLastPw(memberFindPwDto);
            return ResponseEntity.ok("최신비밀번호: " + Pw);
        } else {
            memberService.findLastPw(memberFindPwDto);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입력한 정보와 일치하는 사용자를 찾을 수 없습니다. 이메일로 비밀번호 재설정 안내가 발송되었습니다.");
        }
    }


}

