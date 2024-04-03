package com.firstproject.firstproject.food.foodstorage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
@Tag(name = "FoodStorage-Controller", description = "냉장 냉동 음식 리스트 추가 조회 수정 삭제 유통기한 임박")
public class FoodStorageController {

    private final FoodStorageService foodStorageService;

    // 보관리스트 추가
    @PostMapping("/foodstorage/add/{memberId}")
    @Operation(summary = "보관리스트 추가")
    public ResponseEntity<String> addFoodStorage(@PathVariable Long memberId, @RequestBody FoodStorageDTO request) {
        foodStorageService.addFoodStorage(memberId, request);
        return new ResponseEntity<>("FoodStorage saved successfully", HttpStatus.CREATED);
    }
    // 보관리스트 (전체)조회
    @GetMapping("/foodstorage/{memberid}")
    @Operation(summary = "보관리스트 냉장/냉동 조회")
    public List<FoodStorage> getFoodStorage(@PathVariable Long memberid) {
        List<FoodStorage> foodStorages = foodStorageService.getFoodStorage(memberid);
        return foodStorages;
    }
    // 보관리스트 냉장조회
    @GetMapping("/foodstorage/cold/{memberid}")
    @Operation(summary = "보관리스트 냉장 조회")
    public List<FoodStorage> getColdStorage(@PathVariable Long memberid) {
        List<FoodStorage> coldStorage = foodStorageService.getColdStorage(memberid);
        return coldStorage;
    }
    // 보관리스트 냉동조회
    @GetMapping("/foodstorage/frozen/{memberid}")
    @Operation(summary = "보관리스트 냉동조회")
    public List<FoodStorage> getFrozenStorage(@PathVariable Long memberid) {
        List<FoodStorage> frozenStorage = foodStorageService.getFrozenStorage(memberid);
        return frozenStorage;
    }
    // 보관리스트 수정
    @PutMapping("/foodstorage/update/{foodStorageId}")
    @Operation(summary = "보관리스트 수정")
    public ResponseEntity<String> updateFoodStorage(@PathVariable Long foodStorageId, @RequestBody FoodStorage updatedFoodStorage) {
        foodStorageService.updateFoodStorage(foodStorageId, updatedFoodStorage);
        return ResponseEntity.ok("FoodStorage updated successfully");
    }
    // 보관리스트 삭제
    @DeleteMapping("/foodstorage/delete/{foodStorageId}")
    @Operation(summary = "보관리스트 삭제",description = "보관리스트 삭제")
    public ResponseEntity<String> deleteFoodStorage(@PathVariable Long foodStorageId) {
        foodStorageService.deleteFoodStorage(foodStorageId);
        return ResponseEntity.ok("FoodStorage deleted successfully");
    }
    // 마감일 임박
    @GetMapping("/foodstorage/exp")
    @Operation(summary = "마감일 임박")
    public ResponseEntity<List<FoodStorage>> Expiration(@PathVariable Long member_id) {
        List<FoodStorage> get = foodStorageService.Expiration(member_id);
        return ResponseEntity.ok(get);
    }
}
