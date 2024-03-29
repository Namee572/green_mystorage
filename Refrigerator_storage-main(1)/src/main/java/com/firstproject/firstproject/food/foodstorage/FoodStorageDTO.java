package com.firstproject.firstproject.food.foodstorage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodStorageDTO {
    private int quantity;
    private LocalDateTime regDate;
    private Date expDate;
    private StorageType storageType;
    private Long memberId; // 외래 키인 회원의 식별자
    private Long foodInfoId; // 외래 키인 음식 정보의 식별자
}
