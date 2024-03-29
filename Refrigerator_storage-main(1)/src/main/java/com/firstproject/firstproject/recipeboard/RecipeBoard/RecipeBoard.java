package com.firstproject.firstproject.recipeboard.RecipeBoard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firstproject.firstproject.BaseTimeEntity;
import com.firstproject.firstproject.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;


//domain - entity - Board

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "recipeboard")
public class RecipeBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_pk", nullable = false, unique = true)
    private Long id; // 레시피의 PK = 게시글

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private Member member; // 사용자 엔티티와의 관계

    @Column(nullable = false)
    private String title; // 제목

    @Column(columnDefinition = "TEXT")
    private String content;

    private int viewCount; // 조회수
}