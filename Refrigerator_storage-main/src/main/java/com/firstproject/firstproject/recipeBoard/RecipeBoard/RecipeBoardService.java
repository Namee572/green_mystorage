package com.firstproject.firstproject.recipeBoard.RecipeBoard;



import com.firstproject.firstproject.exception.BoardNotFoundException;
import com.firstproject.firstproject.member.Member;
import com.firstproject.firstproject.exception.ValidationException;
import com.firstproject.firstproject.exception.ValidationExceptionType;
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
  /*  public Page<RecipeBoard> boardList(Pageable pageable) {

        return recipeBoardRepository.findAll(pageable);
    }
*/

    public RecipeBoard select(Long id) {
        return recipeBoardRepository.findByIdWithComments(id);
    }

    // 게시글 검색 처리
    public Page<RecipeBoard> boardSearchList(String searchKeyword, Pageable pageable) {
        return recipeBoardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    public void incrementViewCount(Long id) {
        RecipeBoard recipeBoard = recipeBoardRepository.findById(id).orElse(null);
        if (recipeBoard != null) {
            recipeBoard.setViewCount(recipeBoard.getViewCount() + 1);
            recipeBoardRepository.save(recipeBoard);
        }
    }


    // 게시글 수정 처리
    public RecipeBoard boardUpdate(Long id, RecipeBoardDto recipeBoardDto, Long userId) {
        Optional<RecipeBoard> optionalRecipeBoard = recipeBoardRepository.findById(id);
        if (optionalRecipeBoard.isPresent()) {
            RecipeBoard recipeBoard = optionalRecipeBoard.get();
            // 현재 로그인한 사용자가 글의 작성자와 일치하는지 확인
            if (recipeBoard.getMember().getId().equals(userId)) {
                recipeBoard.setTitle(recipeBoardDto.getTitle());
                recipeBoard.setContent(recipeBoardDto.getContent());
                recipeBoard.setModifiedDate(LocalDateTime.now());
                return recipeBoardRepository.save(recipeBoard);
            } else {
                // 현재 로그인한 사용자가 글의 작성자가 아닌 경우 예외 처리 또는 null 반환
                throw new BoardNotFoundException("해당 글을 수정할 권한이 없습니다.");
            }
        } else {
            return null;
        }
    }

    // 게시글 삭제 처리
    public void boardDelete(Long id, Long userId) {
        Optional<RecipeBoard> optionalRecipeBoard = recipeBoardRepository.findById(id);
        if (optionalRecipeBoard.isPresent()) {
            RecipeBoard recipeBoard = optionalRecipeBoard.get();
            // 현재 로그인한 사용자가 글의 작성자와 일치하는지 확인
            if (recipeBoard.getMember().getId().equals(userId)) {
                recipeBoardRepository.deleteById(id);
            } else {
                // 현재 로그인한 사용자가 글의 작성자가 아닌 경우 예외 처리 또는 다른 작업 수행
                throw new BoardNotFoundException("해당 글을 삭제할 권한이 없습니다.");
            }
        } else {
            throw new BoardNotFoundException("해당 ID의 글을 찾을 수 없습니다.");
        }
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


    public Page<RecipeBoard> searchByTitle(String searchKeyword, Pageable pageable) {

        return recipeBoardRepository.findByTitleContaining(searchKeyword, pageable);

    }


    // 게시글 제목 검색

    // 게시글 최신순 조회
    public List<RecipeBoard> selectOrderByCreateDateDesc(){
        return recipeBoardRepository.findAllByOrderByCreateDateDesc();
    }

    // 조회수 순서로 조회
    public List<RecipeBoard> findAllByOrderByViewCountDesc(){
        return recipeBoardRepository.findAllByOrderByViewCountDesc();
    }

    // 게시글 특정 회원 조회
    public List<RecipeBoard> selectMember(Member member){
        List<RecipeBoard> list = recipeBoardRepository.findAllByMember(member);
        return list;
    }
}


