package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.common.exception.MemberMismatchException.POST_AUTHOR_MISMATCH_MESSAGE;

import com.grasshouse.dorandoran.common.exception.MemberMismatchException;
import com.grasshouse.dorandoran.common.exception.PostNotFoundException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.dto.PostBoundsRequest;
import com.grasshouse.dorandoran.post.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.dto.PostResponse;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.repository.PostRepositorySupport;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostRepositorySupport postRepositorySupport;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request, Member member) {
        Post post = request.toPost(member);
        postRepository.save(post);

        return PostCreateResponse.from(post);
    }

    @Transactional
    public PostResponse showPost(Long id) {
        Post post = findPostById(id);
        return PostResponse.from(post);
    }

    @Transactional
    public List<PostResponse> showPosts() {
        List<Post> posts = postRepository.findAll();
        return PostResponse.listFrom(posts);
    }

    @Transactional
    public List<PostResponse> showPostsInBounds(PostBoundsRequest request) {
        List<Post> posts = postRepositorySupport
            .findPostsInBounds(request.getLeftBound(), request.getRightBound(),
                request.getLowerBound(), request.getUpperBound());
        return PostResponse.listFrom(posts);
    }

    @Transactional
    public void deletePost(Long id, Member member) {
        Post post = findPostById(id);
        if (!post.isSameAuthor(member)) {
            throw new MemberMismatchException(POST_AUTHOR_MISMATCH_MESSAGE);
        }
        post.delete();
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);
    }
}
