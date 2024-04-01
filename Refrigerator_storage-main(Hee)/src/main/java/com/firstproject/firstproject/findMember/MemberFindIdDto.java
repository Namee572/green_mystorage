package com.firstproject.firstproject.findMember;

import lombok.Data;

@Data
public class MemberFindIdDto {
    private String name;
    private String birth;

    public boolean isValid() {
        return name != null || birth != null;
    }
}

