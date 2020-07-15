package com.grasshouse.dorandoran.comment.controller;

import com.grasshouse.dorandoran.comment.service.CommentService;
import com.grasshouse.dorandoran.comment.service.dto.CommentCreateRequest;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private CommentService commentService;

    //TODO: final 키워드 + RequiredArgsConstructor로 변경
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable Long postId,
        @RequestBody CommentCreateRequest commentRequest) {
        Long commentId = commentService.createComment(postId, commentRequest);
        return ResponseEntity
            .created(URI.create("/posts/" + postId + "/comments/" + commentId))
            .build();
    }
}
