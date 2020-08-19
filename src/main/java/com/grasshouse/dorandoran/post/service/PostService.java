package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.common.exception.PostNotFoundException;
import com.grasshouse.dorandoran.common.exception.PostOwnerMissMatchException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.repository.PostRepositorySupport;
import com.grasshouse.dorandoran.post.service.dto.PostBoundsRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private PostRepository postRepository;
    private PostRepositorySupport postRepositorySupport;

    public PostService(PostRepository postRepository, PostRepositorySupport postRepositorySupport) {
        this.postRepository = postRepository;
        this.postRepositorySupport = postRepositorySupport;
    }

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
            throw new PostOwnerMissMatchException();
        }
        postRepository.delete(post);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);
    }
}
