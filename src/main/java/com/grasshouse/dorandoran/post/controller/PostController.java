package com.grasshouse.dorandoran.post.controller;

import com.grasshouse.dorandoran.post.service.PostService;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        PostCreateResponse response = postService.createPost(postCreateRequest);
        return ResponseEntity.created(URI.create("/posts/" + response.getId())).build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> showPosts() {

        return ResponseEntity.ok(postService.showPosts());
    }

}
