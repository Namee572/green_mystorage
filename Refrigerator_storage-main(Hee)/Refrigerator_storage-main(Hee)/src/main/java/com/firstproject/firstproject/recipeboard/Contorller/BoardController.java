package com.firstproject.firstproject.recipeboard.Contorller;


import com.firstproject.firstproject.recipeboard.Entity.Board;
import com.firstproject.firstproject.recipeboard.Service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private final BoardService boardService;

    // 글 작성
    // 글 작성
    @PostMapping("/write")
    public ResponseEntity<String> boardWrite(@RequestBody Board board) {
        boardService.write(board);
        return ResponseEntity.status(HttpStatus.CREATED).body("글 작성이 완료되었습니다.");
    }

    // 글 작성 완료
    @PostMapping("/writepro")
    public ResponseEntity<String> boardWritePro(@RequestBody @Valid Board board, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사에서 오류가 발생한 경우
            return ResponseEntity.badRequest().body("입력된 데이터가 올바르지 않습니다.");
        }

        boardService.write(board);

        return ResponseEntity.status(HttpStatus.CREATED).body("글 작성이 완료되었습니다.");
    }

    // 게시글 리스트
    @GetMapping("/list")
    public ResponseEntity<Page<Board>> boardList(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                 @RequestParam(required = false) String searchKeyword) {
        Page<Board> list = null;
        if (searchKeyword == null) {
            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        return ResponseEntity.ok(list);
    }

    // 게시글 상세페이지
    @GetMapping("/view/{id}")
    public ResponseEntity<Board> boardView(@PathVariable("id") Long id) {
        Board board = boardService.boardView(id);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> boardDelete(@PathVariable("id") Long id) {
        boardService.boardDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body("글 삭제가 완료되었습니다.");
    }

    // 게시글 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<String> boardUpdate(@PathVariable("id") Long id, @RequestBody Board board) {
        Board existingBoard = boardService.boardView(id);
        if (existingBoard != null) {
            existingBoard.setTitle(board.getTitle());
            existingBoard.setContent(board.getContent());
            boardService.update(existingBoard);
            return ResponseEntity.ok("글 수정이 완료되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}