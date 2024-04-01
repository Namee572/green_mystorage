package com.firstproject.firstproject.recipeboard.Repository;

import com.firstproject.firstproject.recipeboard.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
