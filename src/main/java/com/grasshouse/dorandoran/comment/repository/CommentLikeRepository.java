package com.grasshouse.dorandoran.comment.repository;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Boolean existsByMemberIdAndComment(Long memberId, Comment comment);
}
