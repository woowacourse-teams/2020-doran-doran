package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.common.exception.LikeAlreadyExistException;
import com.grasshouse.dorandoran.common.exception.PostLikeNotFoundException;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.domain.PostLike;
import com.grasshouse.dorandoran.post.repository.PostLikeRepository;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.service.dto.PostLikeCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createPostLike(PostLikeCreateRequest request) {
        Post post = postRepository
            .findById(request.getPostId())
            .orElseThrow(PostLikeNotFoundException::new);
        validatePostLikeDuplication(request.getMemberId(), post);
        PostLike postLike = PostLike.builder()
            .memberId(request.getMemberId())
            .post(post)
            .build();
        postLikeRepository.save(postLike);
        return postLike.getId();
    }

    private void validatePostLikeDuplication(Long memberId, Post post) {
        if (!postLikeRepository.findByMemberIdAndPost(memberId, post).isEmpty()) {
            throw new LikeAlreadyExistException();
        }
    }

    @Transactional
    public void deletePostLike(Long postLikeId) {
        PostLike postLike = postLikeRepository.findById(postLikeId)
            .orElseThrow(PostLikeNotFoundException::new);
        postLikeRepository.delete(postLike);
    }
}
