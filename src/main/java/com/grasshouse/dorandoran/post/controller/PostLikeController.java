package com.grasshouse.dorandoran.post.controller;

import com.grasshouse.dorandoran.config.jwt.LoginMember;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.dto.PostLikeCreateRequest;
import com.grasshouse.dorandoran.post.service.PostLikeService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<Void> createPostLike(@RequestBody @Valid PostLikeCreateRequest request,
        @LoginMember Member member) {
        Long postLikeId = postLikeService.createPostLike(request, member);
        return ResponseEntity.created(URI.create("/posts/likes/" + postLikeId)).build();
    }

    @DeleteMapping("/{postLikeId}")
    public ResponseEntity<Void> deletePostLike(@PathVariable Long postLikeId, @LoginMember Member member) {
        postLikeService.deletePostLike(postLikeId, member);
        return ResponseEntity.noContent().build();
    }
}
