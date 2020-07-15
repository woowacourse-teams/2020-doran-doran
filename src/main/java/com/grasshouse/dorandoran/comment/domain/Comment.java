package com.grasshouse.dorandoran.comment.domain;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Post;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member author;

    @ManyToOne
    private Post post;

    private String content;

    private Double distance;

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

    public void setPost(Post post) {
        if (Objects.isNull(this.post)) {
            this.post = post;
            this.post.getComments().add(this);
        }
    }
}
