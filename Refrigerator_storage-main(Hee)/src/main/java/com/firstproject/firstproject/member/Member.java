package com.firstproject.firstproject.member;

import com.firstproject.firstproject.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(name = "MEMBER")
@Schema(description = "Member 테이블 내용임.")
public class Member extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "사용자 ID",description = "table에서 자동으로 생성되는 컬럼입니다.")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @Schema(title = "사용자 email", description = "사용자 이메일을 넣어주시면 됩니다.")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String nickName;

    @Column(nullable = false, length = 6)
    private String birth;

    @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String withdrawn;


    public boolean matchPassword(String checkPassword) {
        return getPassword().equals(checkPassword);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void withdraw() {
        this.withdrawn = "Y";
    }

    public void setWithdrawn(String withdrawn) {
        this.withdrawn = withdrawn;
    }

}

