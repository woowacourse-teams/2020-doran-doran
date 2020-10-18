package com.grasshouse.dorandoran.post.controller;

import com.grasshouse.dorandoran.common.config.jwt.LoginMember;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.dto.PostResponse;
import com.grasshouse.dorandoran.post.service.PostService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(
        @RequestBody @Valid PostCreateRequest request,
        @LoginMember Member member
    ) {
        PostCreateResponse response = postService.createPost(request, member);
        return ResponseEntity.created(URI.create("/posts/" + response.getId())).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> showPost(@PathVariable Long postId) {
        PostResponse response = postService.showPost(postId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @LoginMember Member member) {
        postService.deletePost(postId, member);
        return ResponseEntity.noContent().build();
    }
}
