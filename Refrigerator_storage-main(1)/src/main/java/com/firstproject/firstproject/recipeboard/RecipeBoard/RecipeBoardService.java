package com.firstproject.firstproject.recipeboard.RecipeBoard;

import com.firstproject.firstproject.member.Member;
import com.firstproject.firstproject.recipeboard.Exception.ValidationException;
import com.firstproject.firstproject.recipeboard.Exception.ValidationExceptionType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeBoardService {

    private final RecipeBoardRepository recipeBoardRepository;

    public RecipeBoard write(Member member,RecipeBoardDto recipeDto){
        RecipeBoard recipe = new RecipeBoard();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setContent(recipeDto.getContent());
        recipe.setCreateDate(LocalDateTime.now());
        recipe.setModifiedDate(LocalDateTime.now());
        recipe.setViewCount(recipeDto.getViewCount());
        recipe.setMember(member);
        if (recipe.getTitle() == null || recipe.getTitle().isEmpty()) {
            throw new ValidationException(ValidationExceptionType.MISSING_TITLE);
        } if (recipe.getContent() == null || recipe.getContent().isEmpty()){
            throw new ValidationException(ValidationExceptionType.INVALID_CONTENT);
        }else{
            return recipeBoardRepository.save(recipe);
        }
    }
    public RecipeBoard findByUserId(Member member){
        return (RecipeBoard) recipeBoardRepository.findAllByMember(member);
    }
    // 게시글 리스트 처리
    public Page<RecipeBoard> boardList(Pageable pageable) {

        return recipeBoardRepository.findAll(pageable);
    }

    //게시글 검색 처리
    public Page<RecipeBoard> boardSearchList(String searchKeyword, Pageable pageable) {

        return recipeBoardRepository.findByTitleContaining(searchKeyword, pageable);

    }

    // 게시글 상세 페이지 처리
    public RecipeBoard boardUpdate(Long id, RecipeBoardDto recipeBoardDto) {
        Optional<RecipeBoard>optionalRecipeBoard = recipeBoardRepository.findById(id);
        if (optionalRecipeBoard.isPresent()){
            RecipeBoard recipeBoard = new RecipeBoard();
            recipeBoard.setTitle(recipeBoardDto.getTitle());
            recipeBoard.setContent(recipeBoardDto.getContent());
            recipeBoard.setModifiedDate(LocalDateTime.now());
            return  recipeBoardRepository.save(recipeBoard);
        }else {
            return null;
        }
    }

    // 게시글 삭제 처리
    public void boardDelete(Long id) {

        recipeBoardRepository.deleteById(id);
    }

/*    public void update(RecipeBoard board) {
        // 게시글이 존재하는지 확인
        if (recipeBoardRepository.existsById(board.getId())) {
            // 기존 게시글을 수정
            recipeBoardRepository.save(board);
        } else {
            // 게시글이 존재하지 않으면 예외 처리 또는 다른 작업 수행
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다: " + board.getId());
        }
    }*/
    public RecipeBoard select(Long id){ // id로 찾는 방법 // 너도 그냥 해둘게
        Optional<RecipeBoard> optionalRecipe = recipeBoardRepository.findById(id);
        if (optionalRecipe.isEmpty()){
            return null;
        }
        RecipeBoard recipe = optionalRecipe.get();
        recipe.setViewCount(recipe.getViewCount()+1);
        recipeBoardRepository.save(recipe);
        return recipe;

    }

    public RecipeBoard selecttitle(String title){ // title로 찾는 방법
        Optional<RecipeBoard>optionalRecipe = Optional.ofNullable(recipeBoardRepository.findBytitle(title));
        if(optionalRecipe.isEmpty()){
            return null;
        }
        RecipeBoard recipe = optionalRecipe.get();
        recipe.setViewCount(recipe.getViewCount()+1);
        recipeBoardRepository.save(recipe);
        return recipe;
    }
    public List<RecipeBoard> selectOrderByCreateDateDesc(){
        return recipeBoardRepository.findAllByOrderByCreateDateDesc();
    }

    // 조회수 순서로 조회
    public List<RecipeBoard> findAllByOrderByViewCountDesc(){
        return recipeBoardRepository.findAllByOrderByViewCountDesc();
    }

    public List<RecipeBoard> selectMember(Member member){
        List<RecipeBoard> list = recipeBoardRepository.findAllByMember(member);
        return list;
    }
}


