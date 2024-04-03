package com.firstproject.firstproject.food.foodstorage;

import com.firstproject.firstproject.food.foodinfo.FoodInfo;
import com.firstproject.firstproject.food.foodinfo.FoodInfoRepository;
import com.firstproject.firstproject.member.Member;
import com.firstproject.firstproject.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodStorageService {

    private final MemberRepository memberRepository;

    private final FoodStorageRepository foodStorageRepository;

    private final FoodInfoRepository foodInfoRepository;


    // 보관리스트 추가
    // 프론트에서 JSON 데이터를 memberid, foodinfoid 포함시켜 DTO 던져준다.
    public void addFoodStorage(Long memberId, FoodStorageDTO request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("ID가 " + memberId + "인 회원을 찾을 수 없습니다."));

        FoodInfo foodInfo = foodInfoRepository.findById(request.getFoodInfoId())
                .orElseThrow(() -> new RuntimeException("ID가 " + request.getFoodInfoId() + "인 음식 정보를 찾을 수 없습니다."));

        FoodStorage foodStorage = new FoodStorage();
        foodStorage.setQuantity(request.getQuantity());
        foodStorage.setExpdate(request.getExpDate());
        foodStorage.setStorageType(request.getStorageType());
        foodStorage.setMember(member);
        foodStorage.setFoodInfo(foodInfo);

        // RegDate는 데이터베이스에서 자동으로 설정되므로 여기서는 설정하지 않아도 됩니다.

        foodStorageRepository.save(foodStorage);
    }
    // 보관리스트 전체조회
    public List<FoodStorage> getFoodStorage(Long memberid) {
        Optional<Member> memberOptional = memberRepository.findById(memberid);
        Member member = memberOptional.get();
        return foodStorageRepository.findByMember(member);
    }
    // 보관리스트 냉장조회
    public List<FoodStorage> getColdStorage(Long memberid) {
        Optional<Member> memberOptional = memberRepository.findById(memberid);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return foodStorageRepository.findByMemberAndStorageType(member, StorageType.Cold);
        } else {
            // 멤버가 존재하지 않는 경우 빈 리스트 반환 또는 예외 처리
            return Collections.emptyList();
        }
    }
    // 보관리스트 냉동조회
    public List<FoodStorage> getFrozenStorage(Long memberid) {
        Optional<Member> memberOptional = memberRepository.findById(memberid);

        if(memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return foodStorageRepository.findByMemberAndStorageType(member, StorageType.Forzen);
        } else {
            return Collections.emptyList();
        }
    }
    // 보관리스트 유통기한 (설정날짜)임박 조회
    public List<FoodStorage> Expiration(Long member_id) {
        // LocalDate 오늘날짜 저장, 오늘날짜의 ()의 더한 날짜 설정값을 저장
        LocalDate today = LocalDate.now();
        // 원하는 일의 까지의 +1
        LocalDate getExpDay = today.plusDays(4);
        // LocalDate 날짜를 Date 로 변환 -> Date 로 날짜 조회 위함
        Date expDay = Date.valueOf(getExpDay);

        Optional<FoodStorage> list = foodStorageRepository.findById(member_id);
        if (list.isPresent()) {
            List<FoodStorage> foodStorages = foodStorageRepository.findByExpdateBeforeAndMemberId(expDay, member_id);


            // 각 FoodStorage에 대해 유통기한이 남은 일수를 계산하여 설정
            for (FoodStorage foodStorage : foodStorages) {
                LocalDate expirationDate = foodStorage.getExpdate().toLocalDate();
                Long daysExp = ChronoUnit.DAYS.between(today, expirationDate);
                foodStorage.setDaysExp(daysExp);
            }

            return foodStorages;
        }
        return Collections.emptyList();
    }
    // 보관리스트 수정
    public void updateFoodStorage(Long foodStorageId, FoodStorage updatedFoodStorage) {
        Optional<FoodStorage> foodStorageOptional = foodStorageRepository.findById(foodStorageId);
        if (foodStorageOptional.isPresent()) {
            FoodStorage existingFoodStorage = foodStorageOptional.get();
            existingFoodStorage.setQuantity(updatedFoodStorage.getQuantity());
            Date updatedExpDate = updatedFoodStorage.getExpdate();
            if (updatedExpDate != null) {
                existingFoodStorage.setExpdate(updatedExpDate);
            }
            //existingFoodStorage.setFoodtype(updatedFoodStorage.getFoodtype());
            // 필요한 업데이트 작업 수행
            foodStorageRepository.save(existingFoodStorage);
        }
    }
    // 보관리스트 삭제
    public void deleteFoodStorage(Long foodStorageId) {
        Optional<FoodStorage> foodStorageOptional = foodStorageRepository.findById(foodStorageId);
        foodStorageOptional.ifPresent(FoodStorage -> foodStorageRepository.delete(FoodStorage));
    }
}
