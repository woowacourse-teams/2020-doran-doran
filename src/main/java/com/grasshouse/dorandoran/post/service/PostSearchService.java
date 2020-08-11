package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepositorySupport;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import com.grasshouse.dorandoran.post.service.dto.PostSearchRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostSearchService {

    private PostRepositorySupport postRepositorySupport;

    public PostSearchService(
        PostRepositorySupport postRepositorySupport) {
        this.postRepositorySupport = postRepositorySupport;
    }

    @Transactional
    public List<PostResponse> showSearchResults(PostSearchRequest request) {
        List<Post> searchResults = postRepositorySupport
            .findPostContainsKeywordBetweenDate(request.getKeyword(), request.getStartDate(),
                request.getEndDate());

        return searchResults.stream()
            .map(PostResponse::from)
            .collect(Collectors.toList());
    }

}
