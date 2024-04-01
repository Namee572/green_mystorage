package com.firstproject.firstproject.member;

import lombok.Data;

@Data
public class MemberFindPwDto {

    private String name;
    private String email;
    private String birth;

    public boolean isValid() {
        return name != null && !name.isEmpty() &&
                email != null && !email.isEmpty() &&
                birth != null && !birth.isEmpty();
    }

}
