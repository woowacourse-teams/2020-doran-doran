package com.grasshouse.dorandoran.comment.controller;

import com.grasshouse.dorandoran.comment.service.CommentLikeService;
import com.grasshouse.dorandoran.comment.service.dto.CommentLikeCreateRequest;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://woowacourse.com"})
@RestController
@RequestMapping("/comments/likes")
public class CommentLikeController {

    private CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping
    public ResponseEntity<Void> createCommentLike(
        @RequestBody @Valid CommentLikeCreateRequest request) {
        Long commentLikeId = commentLikeService.createCommentLike(request);
        return ResponseEntity
            .created(URI.create("/comments/likes/" + commentLikeId))
            .build();
    }

    @DeleteMapping("/{commentLikeId}")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable Long commentLikeId) {
        commentLikeService.deleteCommentLike(commentLikeId);
        return ResponseEntity.noContent().build();
    }
}
