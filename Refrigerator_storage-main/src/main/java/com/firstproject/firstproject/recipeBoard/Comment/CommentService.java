package com.firstproject.firstproject.recipeBoard.Comment;


import com.firstproject.firstproject.exception.BoardNotFoundException;
import com.firstproject.firstproject.exception.CommentNotFoundException;
import com.firstproject.firstproject.exception.MemberNotFoundException;
import com.firstproject.firstproject.member.Member;
import com.firstproject.firstproject.member.MemberRepository;

import com.firstproject.firstproject.recipeBoard.RecipeBoard.RecipeBoard;
import com.firstproject.firstproject.recipeBoard.RecipeBoard.RecipeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RecipeBoardRepository recipeBoardRepository;

    // 댓글 생성
    public Comment createComment(Long userId, Long boardId, String content) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        RecipeBoard board = recipeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setMember(user);
        comment.setBoard(board);
        comment.setContent(content);

        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    // 댓글 수정
    public Comment updateComment(Long commentId, String Content, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if (!comment.getMember().getId().equals(userId)) {
            throw new IllegalStateException("댓글을 수정할 권한이 없습니다.");
        }

        comment.setContent(Content);
        Comment updatedComment = commentRepository.save(comment);
        return updatedComment;
    }


    // 댓글 삭제
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if (!comment.getMember().getId().equals(userId)) {
            throw new IllegalStateException("댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}