package com.firstproject.firstproject.recipeboard.Comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Setter
@Getter
@Data
public class CommentDto {

    private Long id;
    private Long boardId;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;


    public CommentDto(Long id, Long boardId, String content, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.boardId = boardId;
        this.content = content;
        this.createDate = createDate; // LocalDate를 String으로 변환하여 저장합니다.
        this.modifiedDate = modifiedDate; // LocalDate를 String으로 변환하여 저장합니다.
    }


}
