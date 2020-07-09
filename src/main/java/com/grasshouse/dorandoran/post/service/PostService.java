package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
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
        return PostCreateResponse.of(post);
    }
}
