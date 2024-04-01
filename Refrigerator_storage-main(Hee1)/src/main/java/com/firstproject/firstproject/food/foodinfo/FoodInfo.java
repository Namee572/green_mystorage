package com.firstproject.firstproject.food.foodinfo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foodinfo")
public class FoodInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodinfo_id;

    @Column(length = 50)
    private String tip;
    @Column(length = 50)
    private String foodname;
}
