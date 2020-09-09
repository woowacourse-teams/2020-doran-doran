package com.grasshouse.dorandoran.comment.domain;

import com.grasshouse.dorandoran.common.baseentity.EntityStatus;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Post;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Length(max = 120, message = "댓글은 120자를 초과할 수 없습니다.")
    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;

    @NotNull
    private Double distance;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentLike> likes = new HashSet<>();

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private EntityStatus status = EntityStatus.ALIVE;

    @Builder
    public Comment(Long id, Member author, Post post, String content, Double distance) {
        this.id = id;
        setAuthor(author);
        setPost(post);
        this.content = content;
        this.distance = distance;
    }

    private void setAuthor(Member author) {
        if (Objects.isNull(this.author)) {
            this.author = author;
            this.author.addComment(this);
        }
    }

    private void setPost(Post post) {
        if (Objects.isNull(this.post)) {
            this.post = post;
            this.post.addComment(this);
        }
    }

    public void delete() {
        this.status = EntityStatus.DELETED;
        this.likes.clear();
    }

    public boolean isSameAuthor(Member member) {
        return author.isSameMember(member);
    }
}
