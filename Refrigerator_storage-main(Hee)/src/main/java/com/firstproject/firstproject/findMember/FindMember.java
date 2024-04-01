package com.firstproject.firstproject.findMember;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name = "member")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    @Size(min = 5,max = 30)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String name; //실명


    @NotBlank
    @Column(nullable = false)
    private String password; // 추후 보안

    @NotBlank
    @Column(nullable = false, length = 6)
    private String birth;

    @NotBlank
    @Column(nullable = false)
    private String sender; //발신자 이메일 주소

    @NotBlank
    @Column(nullable = false)
    private String receiver; //수신자의 이메일 주소

    private String title; // 이메일 제목
    private String message; // 이메일 내용
}
