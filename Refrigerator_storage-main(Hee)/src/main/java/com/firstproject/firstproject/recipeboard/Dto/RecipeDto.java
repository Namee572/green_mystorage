package com.firstproject.firstproject.recipeboard.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class RecipeDto {

    private String title;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registDate;

    private int viewCount;

    private Long memberid;

    public RecipeDto(String title, String content, LocalDateTime registDate, int viewCount, Long memberid) {
        this.title = title;
        this.content = content;
        this.registDate = registDate;
        this.viewCount = viewCount;
        this.memberid = memberid;
    }
}
