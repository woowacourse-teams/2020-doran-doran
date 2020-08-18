package com.grasshouse.dorandoran.post.controller;

import com.grasshouse.dorandoran.post.service.PostService;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody @Valid PostCreateRequest request) {
        PostCreateResponse response = postService.createPost(request);
        return ResponseEntity.created(URI.create("/posts/" + response.getId())).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> showPost(@PathVariable Long postId) {
        PostResponse response = postService.showPost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> showPosts() {
        List<PostResponse> responses = postService.showPosts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/bounds")
    public ResponseEntity<List<PostResponse>> showPostsInBounds(@RequestParam Double leftBound,
        @RequestParam Double rightBound, @RequestParam Double upperBound,
        @RequestParam Double lowerBound) {
        List<PostResponse> responses = postService
            .showPostsInBounds(leftBound, rightBound, upperBound, lowerBound);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
