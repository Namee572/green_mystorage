package com.firstproject.firstproject.recipeboard.Contorller;


import com.firstproject.firstproject.recipeboard.Dto.CommentDto;
import com.firstproject.firstproject.recipeboard.Entity.Comment;
import com.firstproject.firstproject.recipeboard.exception.CommentNotFoundException;
import com.firstproject.firstproject.recipeboard.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto) {
        Comment comment = commentService.createComment(commentDto);
        return ResponseEntity.ok(convertToDto(comment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        try {
            Comment comment = commentService.getCommentById(id);
            return ResponseEntity.ok(convertToDto(comment));
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        try {
            Comment comment = commentService.updateComment(id, commentDto);
            return ResponseEntity.ok(convertToDto(comment));
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private CommentDto convertToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getBoard().getId(),
                comment.getContent(),
                comment.getCreateDate(),
                comment.getModifiedDate()
        );
    }
}
