package com.grasshouse.dorandoran.post.repository;

import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.domain.PostLike;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    List<PostLike> findByMemberIdAndPost(@NotNull Long memberId, @NotNull Post post);
}
