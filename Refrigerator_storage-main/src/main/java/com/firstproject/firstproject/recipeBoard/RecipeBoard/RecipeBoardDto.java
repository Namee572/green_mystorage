package com.firstproject.firstproject.recipeBoard.RecipeBoard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.firstproject.firstproject.member.Member;
import com.firstproject.firstproject.recipeBoard.Comment.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RecipeBoardDto {
    private Long id; // 레시피의 PK
    private Long user_id; // 사용자의 FK
    @NotBlank
    private String title; // 제목
    @NotBlank
    private String content; // 내용
    private List<Comment> comments;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private int viewCount; // 조회수


}
