package com.grasshouse.dorandoran.post.controller;

import com.grasshouse.dorandoran.post.service.PostLikeService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://woowacourse.com"})
@RestController
@RequestMapping("/posts/likes")
public class PostLikeController {

    private PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping
    public ResponseEntity<Void> createPostLike(@RequestParam Long postId,
        @RequestParam Long memberId) {
        Long postLikeId = postLikeService.createPostLike(postId, memberId);
        return ResponseEntity.created(URI.create("/posts/likes/" + postLikeId)).build();
    }

    @DeleteMapping("/{postLikeId}")
    public ResponseEntity<Void> deletePostLike(@PathVariable Long postLikeId) {
        postLikeService.deletePostLike(postLikeId);
        return ResponseEntity.noContent().build();
    }
}
