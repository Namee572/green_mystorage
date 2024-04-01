package com.firstproject.firstproject.recipeboard.Entity;

import com.firstproject.firstproject.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;


//domain - entity - Board

@Entity
@Table(name = "board")
@Data
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String title;
    @Column(nullable = true)
    private String content;



}