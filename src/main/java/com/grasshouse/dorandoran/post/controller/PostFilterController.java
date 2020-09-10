package com.grasshouse.dorandoran.post.controller;

import com.grasshouse.dorandoran.post.dto.PostFilterRequest;
import com.grasshouse.dorandoran.post.dto.PostResponse;
import com.grasshouse.dorandoran.post.service.PostFilterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/filter")
public class PostFilterController {

    private final PostFilterService PostFilterService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> showFilteredPosts(@PostFilter PostFilterRequest request) {
        List<PostResponse> responses = PostFilterService.showFilteredPosts(request);
        return ResponseEntity.ok(responses);
    }
}
