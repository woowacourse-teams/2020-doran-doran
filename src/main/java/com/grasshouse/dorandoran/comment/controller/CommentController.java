package com.grasshouse.dorandoran.comment.controller;

import com.grasshouse.dorandoran.comment.dto.CommentCreateRequest;
import com.grasshouse.dorandoran.comment.service.CommentService;
import com.grasshouse.dorandoran.common.config.jwt.LoginMember;
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
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(
        @RequestBody @Valid CommentCreateRequest request,
        @LoginMember Member member
    ) {
        Long commentId = commentService.createComment(request, member);
        return ResponseEntity
            .created(URI.create("/comments/" + commentId))
            .build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
        @LoginMember Member member) {
        commentService.deleteComment(commentId, member);
        return ResponseEntity.noContent().build();
    }

}
