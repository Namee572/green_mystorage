package com.firstproject.firstproject.recipeBoard.RecipeBoard;



import com.firstproject.firstproject.exception.BoardNotFoundException;
import com.firstproject.firstproject.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Tag(name = "RecipeBoard-Controller",description = "게시글 최신순/조회순조회 작성 수정 삭제  ")
public class RecipeBoardController {

    private final RecipeBoardService recipeBoardService;
    private final RecipeBoardRepository recipeBoardRepository;

    // 글 작성
    @PostMapping("/write/{member}")
    @Operation(summary = "게시판에 글작성")
    public ResponseEntity<String> insert(@PathVariable Member member, @RequestBody RecipeBoardDto recipeDto) {
        RecipeBoard savedRecipeBoard = recipeBoardService.write(member, recipeDto);
        String message = "글 작성이 완료되었습니다";
        return ResponseEntity.ok(message);
    }
    // user 조회
    // localhost:8080/board/find?user_id=2
    @GetMapping("/find")
    @Operation(summary = "해당하는 유저에 대한 글 조회")
    public List<RecipeBoard> findme(@RequestParam Long user_id){
        return recipeBoardRepository.findRecipeBoardsByUserId(user_id);
    }
    //전체 조회 테스트 // 완성
    @GetMapping("/list")
    @Operation(summary = "게시글 전체 조회")
    public List<RecipeBoard> recipeBoardList(){
        List<RecipeBoard>list = recipeBoardRepository.findAll();
        return list;
    }

    // id로 찾기 view로도 찾고 계정에 대한  글을 찾는다.
    @GetMapping("/view/{id}")
    @Operation(summary = "지정된 recipe_id로 게시글을 찾는다.",description = "게시글을 보면 조회수가 증가한다.")
    public ResponseEntity<RecipeBoard> select(@PathVariable Long id){
        RecipeBoard recipeBoard = recipeBoardService.select(id);
        if (recipeBoard == null) {
            return ResponseEntity.notFound().build();
        }

        // 조회수 증가
        recipeBoardService.incrementViewCount(id);

        return ResponseEntity.ok(recipeBoard);
    }
    @GetMapping("/search")
    @Operation(summary = "키워드로 찾기")
    public ResponseEntity<Page<RecipeBoard>> searchByTitle(@RequestParam String keyword,
                                                           @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<RecipeBoard> result = recipeBoardService.boardSearchList(keyword, pageable);
        return ResponseEntity.ok(result);
    }


    // 게시글 삭제 // 살림
    @DeleteMapping("/delete/{userId}/{id}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<String> deleteRecipeBoard(
            @PathVariable Long id,
            @PathVariable Long userId) {
        try {
            recipeBoardService.boardDelete(id, userId);
            return ResponseEntity.ok("게시글이 정상적으로 삭제되었습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 삭제에 실패했습니다");
        }
    }


    // 게시글 수정
    @PutMapping("/update/{userId}/{id}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<String> updateRecipeBoard(
            @PathVariable Long id,
            @RequestBody RecipeBoardDto recipeBoardDto,
            @PathVariable Long userId) {
        try {
            recipeBoardService.boardUpdate(id, recipeBoardDto, userId);
            return ResponseEntity.ok("게시글이 정상적으로 수정되었습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 수정에 실패했습니다");
        }
    }
    ////


    // 최신순
    @GetMapping("/recent")
    @Operation(summary = "게시글 최신순으로 조회")
    public ResponseEntity<List<RecipeBoard>> selectOrderByCreateDateDesc() {
        List<RecipeBoard> recipeBoards = recipeBoardService.selectOrderByCreateDateDesc();
        return ResponseEntity.ok(recipeBoards);

    }

    // 조회수 순서로 조회
    @GetMapping("/view")
    @Operation(summary = "조회순으로 조회")
    public ResponseEntity<List<RecipeBoard>> findAllByOrderByViewCountDesc() {
        List<RecipeBoard> recipeBoards = recipeBoardService.findAllByOrderByViewCountDesc();
        return ResponseEntity.ok(recipeBoards);
    }
    // memberid로 조회
    @GetMapping("/member/{member}")
    @Operation(summary = "memberid(본인이 쓴글)로 조회")
    public ResponseEntity<List<RecipeBoard>> findAllByMember(@PathVariable Member member) {
        List<RecipeBoard> recipeBoards = recipeBoardService.selectMember(member);
        return ResponseEntity.ok(recipeBoards);
    }

}
