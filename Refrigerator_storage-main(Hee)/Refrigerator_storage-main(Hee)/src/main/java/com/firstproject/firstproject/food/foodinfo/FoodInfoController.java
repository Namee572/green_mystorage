package com.firstproject.firstproject.food.foodinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/foodinfo")
@RequiredArgsConstructor
public class FoodInfoController {
    private final FoodInfoService foodInfoService;

    // foodinfo 검색조회
    @GetMapping("")
    public List<FoodInfo> getFoodInfo(@RequestParam("q") String foodInfoName) {
        return foodInfoService.getFoodInfo(foodInfoName);
    }
}
