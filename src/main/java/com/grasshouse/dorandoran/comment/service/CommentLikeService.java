package com.grasshouse.dorandoran.comment.service;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.domain.CommentLike;
import com.grasshouse.dorandoran.comment.repository.CommentLikeRepository;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.comment.service.dto.CommentLikeCreateRequest;
import com.grasshouse.dorandoran.common.exception.CommentLikeAlreadyExistsException;
import com.grasshouse.dorandoran.common.exception.CommentLikeNotFoundException;
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
    public Long createCommentLike(CommentLikeCreateRequest request) {
        Comment comment = commentRepository
            .findById(request.getCommentId())
            .orElseThrow(CommentNotFoundException::new);
        validateCommentLikeDuplication(request.getMemberId(), comment);

        CommentLike commentLike = CommentLike.builder()
            .memberId(request.getMemberId())
            .comment(comment)
            .build();

        commentLikeRepository.save(commentLike);
        return commentLike.getId();
    }

    private void validateCommentLikeDuplication(Long memberId, Comment comment) {
        if (commentLikeRepository.existsByMemberIdAndComment(memberId, comment)) {
            throw new CommentLikeAlreadyExistsException();
        }
    }

    @Transactional
    public void deleteCommentLike(Long commentLikeId) {
        CommentLike commentLike = commentLikeRepository.findById(commentLikeId)
            .orElseThrow(CommentLikeNotFoundException::new);
        commentLikeRepository.delete(commentLike);
    }
}
