package com.firstproject.firstproject.food.foodstorage;

import com.firstproject.firstproject.food.foodinfo.FoodInfo;
import com.firstproject.firstproject.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.print.attribute.standard.MediaSize;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "foodstorage")
@Schema(description = "음식 테이블")
public class FoodStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "음식 ID",description = "table에서 자동으로 생성되는 컬럼입니다.")
    private Long foodstroage_id;

    @Column
    @Schema(name = "음식수량")
    private int quantity;
    @CreatedDate
    @Schema(name = "등록시간")
    private LocalDateTime regdate;
    @Column
    @Schema(name = "유통기한")
    private Date expdate;
    @Transient // DB에 매핑하지 않음
    @Schema(name = "D-day(남은 일자)")
    private Long daysExp;
    @Enumerated(EnumType.STRING)
    @Schema(name = "냉장/냉동 분류")
    private StorageType storageType;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Schema(name = "member_id 를 받아온다")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "foodinfo_id")
    @Schema(name = "foodinfo_id 를 받아온다")
    private FoodInfo foodInfo;
}
