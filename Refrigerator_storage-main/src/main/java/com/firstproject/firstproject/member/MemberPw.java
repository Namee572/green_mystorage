package com.firstproject.firstproject.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberPw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String sender; //발신자 이메일 주소

    @NotBlank
    @Email
    @Column(nullable = false)
    private String receiver; //수신자의 이메일 주소

    private String title; // 이메일 제목
    private String message; // 이메일 내용

//    private LocalDateTime sentDateTime; // 이메일 전송 일시
}

