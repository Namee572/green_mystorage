package com.firstproject.firstproject.food.foodinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodInfoService {
    private final FoodInfoRepository foodInfoRepository;

    // foodinfo 검색조회
    public List<FoodInfo> getFoodInfo(String foodInfoName) {
        return foodInfoRepository.findByFoodnameContaining(foodInfoName);
    }
}
