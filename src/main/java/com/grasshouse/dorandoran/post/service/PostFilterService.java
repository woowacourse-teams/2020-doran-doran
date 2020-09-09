package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.dto.PostFilterRequest;
import com.grasshouse.dorandoran.post.dto.PostResponse;
import com.grasshouse.dorandoran.post.repository.PostRepositorySupport;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostFilterService {

    private final PostRepositorySupport postRepositorySupport;

    @Transactional
    public List<PostResponse> showSearchResults(PostFilterRequest request) {
        List<Post> searchResults = postRepositorySupport
            .findPostWithKeywordAndDate(request.getKeyword(), request.getStartDate(),
                request.getEndDate());

        return searchResults.stream()
            .map(PostResponse::from)
            .collect(Collectors.toList());
    }

}
