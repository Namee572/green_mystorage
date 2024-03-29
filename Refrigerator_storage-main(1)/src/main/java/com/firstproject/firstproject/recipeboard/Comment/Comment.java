package com.firstproject.firstproject.recipeboard.Comment;

import com.firstproject.firstproject.BaseTimeEntity;
import com.firstproject.firstproject.recipeboard.RecipeBoard.RecipeBoard;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Data
@Builder
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_pk", nullable = false, unique = true)
    private Long id; // 레시피의 PK

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private RecipeBoard board;

    @Column(nullable = false)
    private String content;


}
