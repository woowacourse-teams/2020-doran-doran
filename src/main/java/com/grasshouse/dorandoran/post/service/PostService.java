package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.common.exception.PostNotFoundException;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostCreateResponse createPost(PostCreateRequest postCreateRequest) {
        Post post = postCreateRequest.toPost();
        postRepository.save(post);
        return PostCreateResponse.from(post);
    }

    public PostResponse showPost(Long id) {
        Post post = findPostById(id);
        return PostResponse.from(post);
    }

    public List<PostResponse> showPosts() {
        return PostResponse.listFrom(postRepository.findAll());
    }

    public void deletePost(Long id) {
        Post post = findPostById(id);
        postRepository.delete(post);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);
    }
}
