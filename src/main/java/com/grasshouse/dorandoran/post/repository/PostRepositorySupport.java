package com.grasshouse.dorandoran.post.repository;

import static com.grasshouse.dorandoran.post.domain.QPost.post;

import com.grasshouse.dorandoran.common.baseentity.EntityStatus;
import com.grasshouse.dorandoran.post.domain.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Post> findPostWithKeywordAndDate(String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        List<Post> persistPosts = jpaQueryFactory.selectFrom(post)
            .distinct()
            .where(isPostAlive())
            .where(containsKeyword(keyword), betweenDate(startDate, endDate))
            .fetch();

        return persistPosts.stream()
            .map(Post::filterAliveComments)
            .collect(Collectors.toList());
    }

    public Post findPostContainingComments(Long postId) {
        return jpaQueryFactory.selectFrom(post)
            .innerJoin(post.comments)
            .fetchJoin()
            .where(post.id.eq(postId))
            .fetchFirst();
    }

    public Post findPostContainingLikes(Long postId) {
        return jpaQueryFactory.selectFrom(post)
            .innerJoin(post.likes)
            .fetchJoin()
            .where(post.id.eq(postId))
            .fetchFirst();
    }

    private BooleanExpression isPostAlive() {
        return post.status.eq(EntityStatus.ALIVE);
    }

    private BooleanExpression containsKeyword(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        return post.content.contains(keyword);
    }

    private BooleanExpression betweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            return null;
        }
        return post.createdAt.between(startDate, endDate);
    }
}
