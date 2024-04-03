package com.firstproject.firstproject.food.foodinfo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foodinfo")
@Schema(description = "음식 정보 테이블")
public class FoodInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "음식정보 id",description = "table에서 자동으로 생성되는 컬럼입니다.")
    private Long foodinfo_id;

    @Column(length = 1000)
    @Schema(name = "음식 정보 팁")
    private String tip;
    @Column(length = 50)
    @Schema(name = "음식 정보")
    private String foodname;
}
