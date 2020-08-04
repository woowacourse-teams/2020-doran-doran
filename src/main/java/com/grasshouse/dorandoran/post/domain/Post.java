package com.grasshouse.dorandoran.post.domain;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.member.domain.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member author;

    @NotBlank(message = "작성자의 위치 주소는 비어 있을 수 없습니다.")
    private String authorAddress;

    @Length(max = 200, message = "글은 200자를 초과할 수 없습니다.")
    @NotBlank(message = "글의 내용은 비어 있을 수 없습니다.")
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "POST_LIKE_ID")
    private Set<PostLike> likes = new HashSet<>();

    @Embedded
    @NotNull
    private Location location;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "depth1", column = @Column(name = "ADDRESS_DEPTH_1")),
        @AttributeOverride(name = "depth2", column = @Column(name = "ADDRESS_DEPTH_2")),
        @AttributeOverride(name = "depth3", column = @Column(name = "ADDRESS_DEPTH_3"))
    })
    private Address address;

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
}
