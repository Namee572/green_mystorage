package com.firstproject.firstproject.food.foodinfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/foodinfo")
@RequiredArgsConstructor
@Tag(name = "FoodInfo-controller", description = "음식 정보 팁 조회")
public class FoodInfoController {
    private final FoodInfoService foodInfoService;

    // foodinfo 검색조회
    @GetMapping("")
    @Operation(summary = "음식 정보 검색 조회")
    public List<FoodInfo> getFoodInfo(@RequestParam("q") String foodInfoName) {
        return foodInfoService.getFoodInfo(foodInfoName);
    }
}
