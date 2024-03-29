package com.firstproject.firstproject.recipeboard.RecipeBoard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.firstproject.firstproject.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RecipeBoardDto {
    private Long id; // 레시피의 PK
    private Long user_id; // 사용자의 FK
    @NotBlank
    private String title; // 제목
    @NotBlank
    private String content; // 내용
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private int viewCount; // 조회수

    public RecipeBoardDto(Long id, Long user_id, String title, String content, LocalDateTime createDate, LocalDateTime modifiedDate, int viewCount) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.viewCount = viewCount;
    }
}
