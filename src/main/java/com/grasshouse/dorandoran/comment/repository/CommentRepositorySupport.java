package com.grasshouse.dorandoran.comment.repository;

import static com.grasshouse.dorandoran.comment.domain.QComment.comment;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositorySupport extends QuerydslRepositorySupport {

    private JPAQueryFactory jpaQueryFactory;

    public CommentRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Comment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Comment findCommentContainingLikes(Long commentId) {
        return jpaQueryFactory.selectFrom(comment)
            .innerJoin(comment.likes)
            .fetchJoin()
            .where(comment.id.eq(commentId))
            .fetchFirst();
    }
}
