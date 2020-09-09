package com.grasshouse.dorandoran.comment.controller;

import com.grasshouse.dorandoran.comment.dto.CommentLikeCreateRequest;
import com.grasshouse.dorandoran.comment.service.CommentLikeService;
import com.grasshouse.dorandoran.config.jwt.LoginMember;
import com.grasshouse.dorandoran.member.domain.Member;
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
@RequestMapping("/comments/likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseEntity<Void> createCommentLike(@RequestBody @Valid CommentLikeCreateRequest request,
        @LoginMember Member member) {
        Long commentLikeId = commentLikeService.createCommentLike(request, member);
        return ResponseEntity
            .created(URI.create("/comments/likes/" + commentLikeId))
            .build();
    }

    @DeleteMapping("/{commentLikeId}")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable Long commentLikeId,
        @LoginMember Member member) {
        commentLikeService.deleteCommentLike(commentLikeId, member);
        return ResponseEntity.noContent().build();
    }
}
