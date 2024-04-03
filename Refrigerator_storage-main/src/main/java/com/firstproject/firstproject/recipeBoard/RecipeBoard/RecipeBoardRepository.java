package com.firstproject.firstproject.recipeBoard.RecipeBoard;

import com.firstproject.firstproject.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecipeBoardRepository extends JpaRepository<RecipeBoard, Long> {

    Page<RecipeBoard> findByTitleContaining(String searchKeyword, Pageable pageable);

    List<RecipeBoard> findAllByOrderByCreateDateDesc();

    List<RecipeBoard> findAllByOrderByViewCountDesc();

    List<RecipeBoard> findAllByMember(Member member);

    @Query("SELECT r FROM RecipeBoard r LEFT JOIN FETCH r.comments WHERE r.id = ?1")
    RecipeBoard findByIdWithComments(Long id);

    @Query(value = "SELECT * FROM recipeboard r WHERE r.user_id = :user_id", nativeQuery = true)
    List<RecipeBoard> findRecipeBoardsByUserId(@Param("user_id") Long userId);


}