package com.firstproject.firstproject.member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {

    private String nickName;
    private String email;
    private String name;
    private String birth;

    @Builder
    public MemberInfoDto(Member member) {
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.name = member.getName();
        this.birth = member.getBirth();

    }

}

