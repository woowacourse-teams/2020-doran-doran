package com.grasshouse.dorandoran.post.repository;

import static com.grasshouse.dorandoran.post.domain.QPost.post;

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

    public List<Post> findPostContainsKeywordBetweenDate(String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaQueryFactory
            .selectFrom(post)
            .where(containsKeyword(keyword), betweenDate(startDate, endDate))
            .fetch();
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

    public Post findPostWithLikes(Long postId) {
        return jpaQueryFactory.selectFrom(post)
            .innerJoin(post.likes)
            .fetchJoin()
            .where(post.id.eq(postId))
            .fetchFirst();
    }
}
