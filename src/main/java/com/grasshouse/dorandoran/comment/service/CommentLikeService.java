package com.grasshouse.dorandoran.comment.service;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.domain.CommentLike;
import com.grasshouse.dorandoran.comment.repository.CommentLikeRepository;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.common.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long createCommentLike(Long commentId, Long memberId) {
        Comment comment = commentRepository
            .findById(commentId)
            .orElseThrow(CommentNotFoundException::new);
        CommentLike commentLike = CommentLike.builder()
            .memberId(memberId)
            .comment(comment)
            .build();
        commentLikeRepository.save(commentLike);
        return commentLike.getId();
    }
}
