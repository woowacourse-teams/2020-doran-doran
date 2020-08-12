package com.grasshouse.dorandoran.comment.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Comment comment;

    @Builder
    public CommentLike(Long id, Long memberId, Comment comment) {
        this.id = id;
        this.memberId = memberId;
        setComment(comment);
    }

    private void setComment(Comment comment) {
        if (Objects.isNull(this.comment)) {
            this.comment = comment;
            this.comment.getLikes().add(this);
        }
    }
}
