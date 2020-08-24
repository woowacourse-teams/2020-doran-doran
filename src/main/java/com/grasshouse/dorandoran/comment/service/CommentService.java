package com.grasshouse.dorandoran.comment.service;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.comment.service.dto.CommentCreateRequest;
import com.grasshouse.dorandoran.common.exception.CommentAuthorMismatchException;
import com.grasshouse.dorandoran.common.exception.CommentNotFoundException;
import com.grasshouse.dorandoran.common.exception.PostNotFoundException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createComment(CommentCreateRequest request, Member member) {
        Comment comment = convertToComment(request, member);
        commentRepository.save(comment);
        return comment.getId();
    }

    private Comment convertToComment(CommentCreateRequest request, Member member) {
        Post post = postRepository.findById(request.getPostId())
            .orElseThrow(PostNotFoundException::new);
        Double distance = post.getLocation()
            .calculateDistance(request.getLocation());

        return Comment.builder()
            .author(member)
            .post(post)
            .content(request.getContent())
            .distance(distance)
            .build();
    }

    @Transactional
    public void deleteComment(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(CommentNotFoundException::new);

        if (!comment.isSameAuthor(member)) {
            throw new CommentAuthorMismatchException();
        }

        Post post = comment.getPost();
        post.removeComment(comment);
    }
}
