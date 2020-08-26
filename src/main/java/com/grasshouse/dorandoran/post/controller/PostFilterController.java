package com.grasshouse.dorandoran.post.controller;

import com.grasshouse.dorandoran.post.service.PostFilterService;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import com.grasshouse.dorandoran.post.service.dto.PostFilterRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/filter")
public class PostFilterController {

    private PostFilterService PostFilterService;

    public PostFilterController(PostFilterService PostFilterService) {
        this.PostFilterService = PostFilterService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> showFilteredResults(
            @PostFilter PostFilterRequest request) {
        List<PostResponse> responses = PostFilterService.showSearchResults(request);
        return ResponseEntity.ok(responses);
    }
}
