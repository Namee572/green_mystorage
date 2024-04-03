package com.firstproject.firstproject.recipeBoard.Comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firstproject.firstproject.BaseTimeEntity;
import com.firstproject.firstproject.member.Member;
import com.firstproject.firstproject.recipeBoard.RecipeBoard.RecipeBoard;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_pk")
    private Long id; // 레시피의 PK

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private Member member; // 사용자 엔티티와의 관계

    private String nickname;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id", nullable = false)
    private RecipeBoard board;

    @Column(nullable = false)
    private String content;


}
