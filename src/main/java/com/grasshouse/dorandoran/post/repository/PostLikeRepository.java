package com.grasshouse.dorandoran.post.repository;

import com.grasshouse.dorandoran.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
