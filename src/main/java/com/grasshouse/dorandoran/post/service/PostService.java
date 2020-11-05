package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.common.exception.MemberMismatchException.POST_AUTHOR_MISMATCH_MESSAGE;

import com.grasshouse.dorandoran.common.exception.MemberMismatchException;
import com.grasshouse.dorandoran.common.exception.PostNotFoundException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.dto.PostResponse;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request, Member member) {
        Post post = request.toPost(member);
        postRepository.save(post);

        return PostCreateResponse.from(post);
    }

    @Transactional
    public PostResponse showPost(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);

        return PostResponse.from(post.filterAliveComments());
    }

    @Transactional
    public void deletePost(Long id, Member member) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);
        if (!post.isSameAuthor(member)) {
            throw new MemberMismatchException(POST_AUTHOR_MISMATCH_MESSAGE);
        }
        post.delete();
    }
}
