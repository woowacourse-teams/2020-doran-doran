package com.grasshouse.dorandoran.post.repository;

import com.grasshouse.dorandoran.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
