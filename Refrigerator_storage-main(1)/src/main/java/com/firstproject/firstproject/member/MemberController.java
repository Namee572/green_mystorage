package com.firstproject.firstproject.member;

import com.firstproject.firstproject.member.exception.MemberException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "Member-Controller", description = "멤버 등록 로긴 수정 삭제 조회")
public class MemberController {

    private final MemberService memberService;

    /* 회원가입 */

    @PostMapping("/member/join")
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
    public ResponseEntity<String> login(@Valid @RequestBody MemberDto.LoginDto loginDto)  {
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
    public String logout() {
        memberService.logout();
        return "redirect:/member/login";
    }


    /* 회원정보 수정 */

    @PutMapping("/member/update")
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
    public ResponseEntity<String> update(@Valid @RequestBody MemberDto.UpdatePasswordDto updatePasswordDto) {
        try {
            memberService.updatePassword(updatePasswordDto.asIsPassword(), updatePasswordDto.checkPassword(), updatePasswordDto.toBePassword(), memberService.getLoggedUser());
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
    public ResponseEntity getMyInfo(HttpServletResponse response) {

        MemberInfoDto memberInfoDto = memberService.getMyInfo();
        return new ResponseEntity(memberInfoDto, HttpStatus.OK);
    }
}


// 준재님
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("users")
//public class MemberController {
//
//    private final MemberService userService;
//
//    @GetMapping()
//    public ResponseEntity<List<Member>> getusers(Member user){
//
//        List<Member> list = userService.getusers(user);
//
//        System.out.println(user);
//        return ResponseEntity.status(HttpStatus.OK).body(list);
//    }
//
//
//    @PutMapping()
//    public ResponseEntity<Member> update(@RequestParam String nickname, @RequestBody @Valid MemberDto userDto){
//
//        ModelMapper modelMapper = new ModelMapper();
//        Member user = modelMapper.map(userDto, Member.class);
//        user.setUdate(LocalDateTime.now());
//        System.out.println(user);
//        Member dbuser = userService.update(nickname,user);
//        System.out.println(dbuser);
//
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dbuser);
//
//    }
//
//}