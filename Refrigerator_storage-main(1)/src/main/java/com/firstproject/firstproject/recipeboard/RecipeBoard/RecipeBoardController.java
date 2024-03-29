package com.firstproject.firstproject.recipeboard.RecipeBoard;



import com.firstproject.firstproject.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class RecipeBoardController {

    private final RecipeBoardService recipeBoardService;
    private final RecipeBoardRepository recipeBoardRepository;

    // 글 작성
    @PostMapping("/write/{member}")
    public RecipeBoard insert(@PathVariable Member member ,@RequestBody RecipeBoardDto recipeDto){
        return recipeBoardService.write(member,recipeDto);
    }
    // user 조회
    // localhost:8080/board/find?user_id=2
    @GetMapping("/find")
    public List<RecipeBoard> findme(@RequestParam Long user_id){
        return recipeBoardRepository.findRecipeBoardsByUserId(user_id);
    }

    // 글 작성 완료
/*    @PostMapping("/writepro")
    public ResponseEntity<String> boardWritePro(@RequestBody @Valid RecipeBoard board, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사에서 오류가 발생한 경우
            return ResponseEntity.badRequest().body("입력된 데이터가 올바르지 않습니다.");
        }
        try {
            recipeBoardService.insert(board);
            return ResponseEntity.status(HttpStatus.CREATED).body("글 작성이 완료되었습니다.");
        } catch (ValidationException ex) {
            if (ex.getType() == ValidationExceptionType.MISSING_TITLE && ex.getType() == ValidationExceptionType.INVALID_CONTENT) {
                // 제목과 내용이 모두 비어있는 경우
                return ResponseEntity.status(ex.getType().getHttpStatus()).body("제목과 내용이 모두 비어있습니다.");
            } else {
                // 그 외의 경우
                return ResponseEntity.status(ex.getType().getHttpStatus()).body(ex.getType().getErrorMessage());
            }
        }
    }*/
    // 게시글 리스트
/*    @GetMapping("/list")
    public ResponseEntity<Page<RecipeBoard>> boardList(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                 @RequestParam(required = false) String searchKeyword) {
        Page<RecipeBoard> list = null;
        if (searchKeyword == null) {
            list = recipeBoardService.boardList(pageable);
        } else {
            list = recipeBoardService.boardSearchList(searchKeyword, pageable);
        }

        return ResponseEntity.ok(list);
    }*/

    //전체 조회 테스트 // 완성
    @GetMapping("/list")
    public List<RecipeBoard> recipeBoardList(){
        List<RecipeBoard>list = recipeBoardRepository.findAll();
        return list;
    }

    // id로 찾기
    @GetMapping("/id/{id}")
    public Optional<RecipeBoard> select(@PathVariable Long id){
    return Optional.ofNullable(recipeBoardService.select(id));
    }
    // title로 찾기
    @GetMapping("/title/{title}")
    public RecipeBoard selectByTitle(@PathVariable String title){
        return recipeBoardService.selecttitle(title);
    }

    // 게시글 삭제 // 살림
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> boardDelete(@PathVariable("id") Long id) {
        recipeBoardService.boardDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body("글 삭제가 완료되었습니다.");
    }

    // 게시글 수정
    @PutMapping("/update/{id}")
    public RecipeBoard boardUpdate(@PathVariable Long id, @RequestBody RecipeBoardDto recipeBoardDto) {
    return recipeBoardService.boardUpdate(id, recipeBoardDto);
    }


    ////


    // 최신순
    @GetMapping("/recent")
    public List<RecipeBoard> selectOrderByCreateDateDesc(){
        return recipeBoardService.selectOrderByCreateDateDesc();
    }
    // view 카운트 순
    @GetMapping("/view")
    public List<RecipeBoard> findAllByOrderByViewCountDesc(){
        return recipeBoardService.findAllByOrderByViewCountDesc();
    }
    // 개인조회
    @GetMapping("/member/{member}")
    public List<RecipeBoard> findAllByMember(@PathVariable Member member){
        return recipeBoardService.selectMember(member);
    }
}
