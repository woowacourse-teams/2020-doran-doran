package com.grasshouse.dorandoran.comment.service;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.domain.CommentLike;
import com.grasshouse.dorandoran.comment.repository.CommentLikeRepository;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.common.exception.CommentLikeNotFoundException;
import com.grasshouse.dorandoran.common.exception.CommentNotFoundException;
import com.grasshouse.dorandoran.common.exception.LikeAlreadyExistException;
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
        validateCommentLikeValidation(memberId, comment);
        CommentLike commentLike = CommentLike.builder()
            .memberId(memberId)
            .comment(comment)
            .build();
        commentLikeRepository.save(commentLike);
        return commentLike.getId();
    }

    private void validateCommentLikeValidation(Long memberId, Comment comment) {
        if (!commentLikeRepository.findByMemberIdAndComment(memberId, comment).isEmpty()) {
            throw new LikeAlreadyExistException();
        }
    }

    @Transactional
    public void deleteCommentLike(Long commentLikeId) {
        CommentLike commentLike = commentLikeRepository.findById(commentLikeId)
            .orElseThrow(CommentLikeNotFoundException::new);
        commentLikeRepository.delete(commentLike);
    }
}