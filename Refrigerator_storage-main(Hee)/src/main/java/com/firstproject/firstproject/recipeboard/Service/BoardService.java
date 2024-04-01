package com.firstproject.firstproject.recipeboard.Service;


import com.firstproject.firstproject.recipeboard.Entity.Board;
import com.firstproject.firstproject.recipeboard.exception.ValidationException;
import com.firstproject.firstproject.recipeboard.exception.ValidationExceptionType;
import com.firstproject.firstproject.recipeboard.Repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class BoardService {

    @Autowired
    private final BoardRepository boardRepository;


    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 글 작성 처리
    public void write(Board board) {
        if (board.getTitle() == null || board.getTitle().isEmpty()) {
            throw new ValidationException(ValidationExceptionType.MISSING_TITLE);
        } if (board.getContent() == null || board.getContent().isEmpty()){
            throw new ValidationException(ValidationExceptionType.INVALID_CONTENT);
        }
        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    //게시글 검색 처리
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchKeyword, pageable);

    }

    // 게시글 상세 페이지 처리
    public Board boardView(Long id) {

        return boardRepository.findById(id).orElse(null);
    }

    // 게시글 삭제 처리
    public void boardDelete(Long id) {

        boardRepository.deleteById(id);
    }

    public void update(Board board) {
        // 게시글이 존재하는지 확인
        if (boardRepository.existsById(board.getId())) {
            // 기존 게시글을 수정
            boardRepository.save(board);
        } else {
            // 게시글이 존재하지 않으면 예외 처리 또는 다른 작업 수행
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다: " + board.getId());
        }
    }

}
