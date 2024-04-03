package com.firstproject.firstproject.recipeBoard.RecipeBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.firstproject.firstproject.BaseTimeEntity;
import com.firstproject.firstproject.member.Member;
import com.firstproject.firstproject.recipeBoard.Comment.Comment;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;


//domain - entity - Board

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//    @JsonIgnore
    @JsonManagedReference
    private List<Comment> comments;

    @Column(nullable = false)
    private String title; // 제목

    @Column(columnDefinition = "TEXT")
    private String content;

    private int viewCount; // 조회수

}