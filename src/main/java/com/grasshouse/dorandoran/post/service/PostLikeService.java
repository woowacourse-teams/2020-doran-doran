package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.common.exception.PostLikeNotFoundException;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.domain.PostLike;
import com.grasshouse.dorandoran.post.repository.PostLikeRepository;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createPostLike(Long postId, Long memberId) {
        Post post = postRepository
            .findById(postId)
            .orElseThrow(PostLikeNotFoundException::new);
        PostLike postLike = PostLike.builder()
            .memberId(memberId)
            .post(post)
            .build();
        postLikeRepository.save(postLike);
        return postLike.getId();
    }
}
