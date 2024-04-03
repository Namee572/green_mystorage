package com.firstproject.firstproject.recipeBoard.Comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto
{


    private Long id;
    private Long user_id;
    private Long boardId;
    private String content;
    private String nickname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;




}
