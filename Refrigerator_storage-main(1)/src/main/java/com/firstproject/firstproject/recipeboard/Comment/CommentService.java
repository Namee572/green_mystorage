package com.firstproject.firstproject.recipeboard.Comment;


import com.firstproject.firstproject.recipeboard.Exception.CommentNotFoundException;
import com.firstproject.firstproject.recipeboard.Exception.ValidationException;
import com.firstproject.firstproject.recipeboard.Exception.ValidationExceptionType;
import com.firstproject.firstproject.recipeboard.RecipeBoard.RecipeBoard;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private final CommentRepository commentRepository;

    public Comment createComment(CommentDto commentDto) {
        if (commentDto.getContent() == null || commentDto.getContent().isEmpty()){
            throw new ValidationException(ValidationExceptionType.INVALID_CONTENT);
        }
        Comment comment = convertTo(commentDto);
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
    }

    public Comment updateComment(Long id, CommentDto commentDto) {
        Comment comment = getCommentById(id);
        updateCommentFromDto(comment, commentDto);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = getCommentById(id);
        commentRepository.delete(comment);
    }
    private Comment convertTo(CommentDto commentDto) {
        RecipeBoard board = new RecipeBoard();
        board.setId(commentDto.getBoardId());

        return Comment.builder()
                .id(commentDto.getId())
                .board(board)
                .content(commentDto.getContent())
                .build();
    }


    private void updateCommentFromDto(Comment comment, CommentDto commentDto){
        comment.setContent((commentDto.getContent()));
        if (commentDto.getBoardId() != null) {
            RecipeBoard board = new RecipeBoard();
            board.setId(commentDto.getBoardId());
            comment.setBoard(board);
        } else {
            comment.setBoard(null);
        }
    comment.setModifiedDate(commentDto.getModifiedDate());
    }

}