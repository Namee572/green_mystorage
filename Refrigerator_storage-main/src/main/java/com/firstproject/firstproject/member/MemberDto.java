package com.firstproject.firstproject.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record MemberDto() {


    /* 회원가입 */

    public record SignUpDto(
            @NotBlank(message = "이메일을 입력해 주세요.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            @Size(min = 3, message = "이메일은 최소 3자 이상으로 입력해 주세요.")
            String email,

            @NotBlank(message = "비밀번호를 입력해 주세요.")
            @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", message = "비밀번호는 1개 이상의 알파벳, 숫자, 특수문자를 모두 포함해야 합니다.")
            @Size(min = 8, max = 30, message = "비밀번호는 최소 8자리에서 최대 30자리입니다.")
            String password,

            @NotBlank(message = "비밀번호를 다시 입력해 주세요.")
            String checkPassword,

            @NotBlank(message = "이름을 입력해 주세요.")
            @Size(max = 30, message = "이름은 최대 30글자입니다.")
            @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳만 입력해 주세요.")
            String name,

            @NotBlank(message = "닉네임을 입력해 주세요.")
            @Size(min = 2, max = 16, message = "닉네임은 최소 2자리에서 최대 16자리입니다.")
            String nickName,

            @NotBlank(message = "생년월일을 6자리로 입력해 주세요.")
            @Size(min = 6, max = 6, message = "6자리의 생년월일을 입력해 주세요.")
            String birth) {

        public Member toEntity() {
            return Member.builder()
                    .email(email())
                    .password(password())
                    .name(name)
                    .nickName(nickName)
                    .birth(birth)
                    .build();
        }
    }


    /* 로그인 */

    public record LoginDto(
            @NotBlank(message = "이메일을 입력해 주세요.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String email,

            @NotBlank(message = "비밀번호를 입력해 주세요.")
            String password) {
    }


    /* 회원정보 수정 */

    public record UpdateDto(Optional<String> nickName,
                            Optional<String> name) {
    }


    /* 비밀번호 수정 */

    public record UpdatePasswordDto(
            @NotBlank(message = "현재 비밀번호를 입력해 주세요.")
            String asIsPassword,

            @NotBlank(message = "새 비밀번호를 입력해 주세요.")
            @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", message = "비밀번호는 1개 이상의 알파벳, 숫자, 특수문자를 모두 포함해야 합니다.")
            @Size(min = 8, max = 30, message = "비밀번호는 최소 8자리에서 최대 30자리입니다.")
            String toBePassword,

            @NotBlank(message = "비밀번호를 다시 입력해 주세요.")
            String checkPassword) {
    }


    /* 회원탈퇴 */

    public record WithdrawDto(
            @NotBlank(message = "비밀번호를 입력해주세요.")
            String checkPassword) {
    }



}









