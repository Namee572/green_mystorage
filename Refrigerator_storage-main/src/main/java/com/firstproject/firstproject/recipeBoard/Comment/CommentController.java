package com.firstproject.firstproject.recipeBoard.Comment;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "Comment-Controller",description ="작성된 글에 댓글 생성 수정 삭제 ")
public class CommentController {

    @Autowired
    private final CommentService commentService;


    // 댓글 생성
    @PostMapping("/create/{userId}/{boardId}")
    @Operation(summary = "게시판에 대한 댓글 생성")
    public ResponseEntity<String> createComment(@PathVariable Long userId,
                                                @PathVariable Long boardId,
                                                @RequestBody Map<String, String> requestBody) {
        String content = requestBody.get("content");
        Comment createdComment = commentService.createComment(userId, boardId, content);
        return ResponseEntity.ok("댓글이 성공적으로 작성되었습니다.");
    }

    // 댓글 수정
    @PutMapping("/{commentId}/{userId}")
    @Operation(summary = "게시판 댓글 수정")
    public ResponseEntity<String> updateComment(@PathVariable Long userId,
                                                @PathVariable Long commentId,
                                                @RequestBody Map<String, String> requestBody) {
        String updatedContent = requestBody.get("content");
        Comment updatedComment = commentService.updateComment(commentId, updatedContent, userId);
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}/{userId}")
    @Operation(summary = "게시판 댓글 삭제")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @PathVariable Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }
}