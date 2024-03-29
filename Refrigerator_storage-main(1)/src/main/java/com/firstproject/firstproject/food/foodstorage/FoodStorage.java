package com.firstproject.firstproject.food.foodstorage;

import com.firstproject.firstproject.food.foodinfo.FoodInfo;
import com.firstproject.firstproject.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class FoodStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodstroage_id;

    @Column
    private int quantity;
    @CreatedDate
    private LocalDateTime regdate;
    @Column
    private Date expdate;
    @Transient // DB에 매핑하지 않음
    private Long daysExp;
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "foodinfo_id")
    private FoodInfo foodInfo;
}
