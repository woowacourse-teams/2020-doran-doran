package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepositorySupport;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PostSearchService {

    private PostRepositorySupport postRepositorySupport;

    public PostSearchService(
        PostRepositorySupport postRepositorySupport) {
        this.postRepositorySupport = postRepositorySupport;
    }

    @Transactional
    public List<PostResponse> showSearchResults(String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        List<Post> searchResults = postRepositorySupport.findPostContainsKeywordBetweenDate(keyword, startDate, endDate);

        return searchResults.stream()
            .map(PostResponse::from)
            .collect(Collectors.toList());
    }

}
