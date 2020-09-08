package com.grasshouse.dorandoran.comment.service;

import static com.grasshouse.dorandoran.common.exception.MemberMismatchException.COMMENT_LIKER_MISMATCH_MESSAGE;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.domain.CommentLike;
import com.grasshouse.dorandoran.comment.repository.CommentLikeRepository;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.comment.dto.CommentLikeCreateRequest;
import com.grasshouse.dorandoran.common.exception.CommentLikeAlreadyExistsException;
import com.grasshouse.dorandoran.common.exception.CommentLikeNotFoundException;
import com.grasshouse.dorandoran.common.exception.CommentNotFoundException;
import com.grasshouse.dorandoran.common.exception.MemberMismatchException;
import com.grasshouse.dorandoran.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long createCommentLike(CommentLikeCreateRequest request, Member member) {
        Comment comment = commentRepository
            .findById(request.getCommentId())
            .orElseThrow(CommentNotFoundException::new);
        validateCommentLikeDuplication(member.getId(), comment);

        CommentLike commentLike = CommentLike.builder()
            .memberId(member.getId())
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
    public void deleteCommentLike(Long commentLikeId, Member member) {
        CommentLike commentLike = commentLikeRepository.findById(commentLikeId)
            .orElseThrow(CommentLikeNotFoundException::new);
        if (!commentLike.isSameLiker(member)) {
            throw new MemberMismatchException(COMMENT_LIKER_MISMATCH_MESSAGE);
        }
        commentLikeRepository.delete(commentLike);
    }
}
