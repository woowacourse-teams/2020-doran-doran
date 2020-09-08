package com.grasshouse.dorandoran.comment.domain;

import com.grasshouse.dorandoran.member.domain.Member;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
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

    public boolean isSameLiker(Member member) {
        return this.memberId.equals(member.getId());
    }
}
