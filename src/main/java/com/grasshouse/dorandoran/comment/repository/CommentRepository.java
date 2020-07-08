package com.grasshouse.dorandoran.comment.repository;

import com.grasshouse.dorandoran.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
