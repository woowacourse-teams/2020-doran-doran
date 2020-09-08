package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.common.exception.MemberMismatchException.POST_LIKER_MISMATCH_MESSAGE;

import com.grasshouse.dorandoran.common.exception.MemberMismatchException;
import com.grasshouse.dorandoran.common.exception.PostLikeAlreadyExistsException;
import com.grasshouse.dorandoran.common.exception.PostLikeNotFoundException;
import com.grasshouse.dorandoran.common.exception.PostNotFoundException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.domain.PostLike;
import com.grasshouse.dorandoran.post.dto.PostLikeCreateRequest;
import com.grasshouse.dorandoran.post.repository.PostLikeRepository;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createPostLike(PostLikeCreateRequest request, Member member) {
        Post post = postRepository
            .findById(request.getPostId())
            .orElseThrow(PostNotFoundException::new);
        validatePostLikeDuplication(member.getId(), post);

        PostLike postLike = PostLike.builder()
            .memberId(member.getId())
            .post(post)
            .build();

        postLikeRepository.save(postLike);
        return postLike.getId();
    }

    private void validatePostLikeDuplication(Long memberId, Post post) {
        if (postLikeRepository.existsByMemberIdAndPost(memberId, post)) {
            throw new PostLikeAlreadyExistsException();
        }
    }

    @Transactional
    public void deletePostLike(Long postLikeId, Member member) {
        PostLike postLike = postLikeRepository.findById(postLikeId)
            .orElseThrow(PostLikeNotFoundException::new);
        if (!postLike.isSameLiker(member)) {
            throw new MemberMismatchException(POST_LIKER_MISMATCH_MESSAGE);
        }
    }
}
