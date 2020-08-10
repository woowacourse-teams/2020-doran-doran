package com.grasshouse.dorandoran.comment.controller;

import com.grasshouse.dorandoran.comment.service.CommentLikeService;
import com.grasshouse.dorandoran.comment.service.CommentService;
import com.grasshouse.dorandoran.comment.service.dto.CommentCreateRequest;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private CommentService commentService;
    private CommentLikeService commentLikeService;

    //TODO: final 키워드 + RequiredArgsConstructor로 변경
    public CommentController(CommentService commentService, CommentLikeService commentLikeService) {
        this.commentService = commentService;
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/comments")
    public ResponseEntity<Void> createComment(@RequestBody @Valid CommentCreateRequest request) {
        Long commentId = commentService.createComment(request);
        return ResponseEntity
            .created(URI.create("/comments/" + commentId))
            .build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments/likes")
    public ResponseEntity<Void> createCommentLike(@RequestParam Long commentId,
        @RequestParam Long memberId) {
        Long commentLikeId = commentLikeService.createCommentLike(commentId, memberId);
        return ResponseEntity
            .created(URI.create("/comments/likes/" + commentLikeId))
            .build();
    }

    @DeleteMapping("/comments/likes/{commentLikeId}")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable Long commentLikeId) {
        commentLikeService.deleteCommentLike(commentLikeId);
        return ResponseEntity.noContent().build();
    }
}
