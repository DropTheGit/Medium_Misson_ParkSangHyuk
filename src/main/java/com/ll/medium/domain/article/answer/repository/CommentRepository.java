package com.ll.medium.domain.article.answer.repository;

import com.ll.medium.domain.article.answer.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
