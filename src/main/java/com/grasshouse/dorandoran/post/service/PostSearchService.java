package com.grasshouse.dorandoran.post.service;

import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostSearchService {

    private PostRepository postRepository;

    public PostSearchService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public List<PostResponse> showSearchResults(String keyword) {
        List<Post> searchResults = postRepository.findPostsByContentContaining(keyword);

        return searchResults.stream()
            .map(PostResponse::from)
            .collect(Collectors.toList());
    }

}
