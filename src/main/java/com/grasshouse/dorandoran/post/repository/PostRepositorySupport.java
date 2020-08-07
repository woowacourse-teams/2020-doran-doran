package com.grasshouse.dorandoran.post.repository;

import static com.grasshouse.dorandoran.post.domain.QPost.post;

import com.grasshouse.dorandoran.post.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {

    private JPAQueryFactory jpaQueryFactory;

    public PostRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Post findPostWithComment(Long postId) {
        return jpaQueryFactory.selectFrom(post)
            .innerJoin(post.comments)
            .fetchJoin()
            .where(post.id.eq(postId))
            .fetchFirst();
    }

    public List<Post> findPostBetweenDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaQueryFactory.selectFrom(post)
            .where(post.createdAt.between(startDate,endDate))
            .fetch();
    }
}
