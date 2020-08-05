package com.grasshouse.dorandoran.post.controller;

import com.grasshouse.dorandoran.post.service.PostSearchService;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://woowacourse.com"})
@RestController
@RequestMapping("/posts/search")
public class PostSearchController {

    private PostSearchService postSearchService;

    public PostSearchController(PostSearchService postSearchService) {
        this.postSearchService = postSearchService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> showSearchResults(@RequestParam String keyword) {
        List<PostResponse> responses = postSearchService.showSearchResults(keyword);
        return ResponseEntity.ok(responses);
    }
}
