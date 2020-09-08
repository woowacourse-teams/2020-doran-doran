package com.grasshouse.dorandoran.post.domain;

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
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostLike(Long id, Long memberId, Post post) {
        this.id = id;
        this.memberId = memberId;
        setPost(post);
    }

    private void setPost(Post post) {
        if (Objects.isNull(this.post)) {
            this.post = post;
            this.post.getLikes().add(this);
        }
    }

    public boolean isSameLiker(Member member) {
        return this.memberId.equals(member.getId());
    }
}
