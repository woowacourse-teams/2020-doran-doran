package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.common.exception.PostNotFoundException;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<PostResponse> showPosts() {
        return postRepository.findAll()
            .stream()
            .map(PostResponse::of)
            .collect(Collectors.toList());
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);

        postRepository.delete(post);
    }
}
