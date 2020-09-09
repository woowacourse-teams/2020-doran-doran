package com.grasshouse.dorandoran.post.repository;

import static com.grasshouse.dorandoran.comment.domain.QComment.comment;
import static com.grasshouse.dorandoran.post.domain.QPost.post;

import com.grasshouse.dorandoran.common.baseentity.EntityStatus;
import com.grasshouse.dorandoran.post.domain.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    public Post findPostById(Long postId) {
        return jpaQueryFactory.selectFrom(post)
            .leftJoin(post.comments, comment)
            .where(comment.status.eq(EntityStatus.ALIVE))
            .fetchJoin()
            .where(post.id.eq(postId))
            .where(post.status.eq(EntityStatus.ALIVE))
            .fetchFirst();
    }

    public Post findPostContainingComments(Long postId) {
        return jpaQueryFactory.selectFrom(post)
            .innerJoin(post.comments)
            .fetchJoin()
            .where(post.id.eq(postId))
            .fetchFirst();
    }

    public List<Post> findPostsInBounds(Double leftBound, Double rightBound, Double lowerBound,
        Double upperBound) {
        return jpaQueryFactory.selectFrom(post)
            .where(isAlive())
            .where(betweenLatitude(lowerBound, upperBound), betweenLongitude(leftBound, rightBound))
            .fetch();
    }

    public List<Post> findPostWithKeywordAndDate(String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaQueryFactory.selectFrom(post)
            .where(isAlive())
            .where(containsKeyword(keyword), betweenDate(startDate, endDate))
            .fetch();
    }

    public Post findPostContainingLikes(Long postId) {
        return jpaQueryFactory.selectFrom(post)
            .leftJoin(post.likes)
            .fetchJoin()
            .where(post.id.eq(postId))
            .fetchFirst();
    }

    private BooleanExpression isAlive() {
        return post.status.eq(EntityStatus.ALIVE);
    }

    private BooleanExpression betweenLatitude(Double lowerBound, Double upperBound) {
        if (Objects.isNull(lowerBound) || Objects.isNull(upperBound)) {
            return null;
        }
        return post.location.latitude.between(lowerBound, upperBound);
    }

    private BooleanExpression betweenLongitude(Double leftBound, Double rightBound) {
        if (Objects.isNull(leftBound) || Objects.isNull(rightBound)) {
            return null;
        }
        return post.location.longitude.between(leftBound, rightBound);
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
