package com.firstproject.firstproject.recipeboard.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;



    public BoardDto(Long id, String title, String content, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate; // LocalDate를 String으로 변환하여 저장합니다.
        this.modifiedDate = modifiedDate; // LocalDate를 String으로 변환하여 저장합니다.
    }


}