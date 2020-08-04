package com.grasshouse.dorandoran.comment.domain;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Post;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Post post;

    @Length(max = 120, message = "댓글은 120자를 초과할 수 없습니다.")
    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;

    @NotNull
    private Double distance;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany
    @JoinColumn(name = "COMMENT_LIKE_ID")
    private Set<CommentLike> likes = new HashSet<>();

    @Builder
    public Comment(Long id, Member author, Post post, String content, Double distance,
        Set<CommentLike> likes) {
        this.id = id;
        this.author = author;
        setPost(post);
        this.content = content;
        this.distance = distance;
        this.likes = likes;
    }

    private void setPost(Post post) {
        if (Objects.isNull(this.post)) {
            this.post = post;
            this.post.getComments().add(this);
        }
    }
}
