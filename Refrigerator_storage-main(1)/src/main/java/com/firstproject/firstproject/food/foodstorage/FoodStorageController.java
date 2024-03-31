package com.firstproject.firstproject.food.foodstorage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class FoodStorageController {

    private final FoodStorageService foodStorageService;

    // 보관리스트 추가
    @PostMapping("/foodstorage/add/{memberId}")
    public ResponseEntity<String> addFoodStorage(@PathVariable Long memberId, @RequestBody FoodStorageDTO request) {
        foodStorageService.addFoodStorage(memberId, request);
        return new ResponseEntity<>("FoodStorage saved successfully", HttpStatus.CREATED);
    }
    // 보관리스트 (전체)조회
    @GetMapping("/foodstorage/{memberid}")
    public List<FoodStorage> getFoodStorage(@PathVariable Long memberid) {
        List<FoodStorage> foodStorages = foodStorageService.getFoodStorage(memberid);
        return foodStorages;
    }
    // 보관리스트 냉장조회
    @GetMapping("/foodstorage/cold/{memberid}")
    public List<FoodStorage> getColdStorage(@PathVariable Long memberid) {
        List<FoodStorage> coldStorage = foodStorageService.getColdStorage(memberid);
        return coldStorage;
    }
    // 보관리스트 냉동조회
    @GetMapping("/foodstorage/frozen/{memberid}")
    public List<FoodStorage> getFrozenStorage(@PathVariable Long memberid) {
        List<FoodStorage> frozenStorage = foodStorageService.getFrozenStorage(memberid);
        return frozenStorage;
    }
    // 보관리스트 수정
    @PutMapping("/foodstorage/update/{foodStorageId}")
    public ResponseEntity<String> updateFoodStorage(@PathVariable Long foodStorageId, @RequestBody FoodStorage updatedFoodStorage) {
        foodStorageService.updateFoodStorage(foodStorageId, updatedFoodStorage);
        return ResponseEntity.ok("FoodStorage updated successfully");
    }
    // 보관리스트 삭제
    @DeleteMapping("/foodstorage/delete/{foodStorageId}")
    public ResponseEntity<String> deleteFoodStorage(@PathVariable Long foodStorageId) {
        foodStorageService.deleteFoodStorage(foodStorageId);
        return ResponseEntity.ok("FoodStorage deleted successfully");
    }
    // 마감일 임박
    @GetMapping("/foodstorage/exp/{member_id}")
    public ResponseEntity<Optional<FoodStorage>> Expiration(@PathVariable Long member_id) {
        Optional<FoodStorage> get = foodStorageService.Expiration(member_id);
        return ResponseEntity.ok(get);
    }
}
