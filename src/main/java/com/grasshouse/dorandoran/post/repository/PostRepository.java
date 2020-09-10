package com.grasshouse.dorandoran.post.repository;

import com.grasshouse.dorandoran.post.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsByContentContaining(String keyword);
}
