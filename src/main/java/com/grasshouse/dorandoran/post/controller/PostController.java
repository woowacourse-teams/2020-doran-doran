package com.grasshouse.dorandoran.post.controller;

import com.grasshouse.dorandoran.post.service.PostService;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequest request) {
        PostCreateResponse response = postService.createPost(request);
        return ResponseEntity.created(URI.create("/posts/" + response.getId())).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> showPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.showPost(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> showPosts() {
        return ResponseEntity.ok(postService.showPosts());
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

}
