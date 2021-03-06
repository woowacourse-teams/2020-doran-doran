package com.grasshouse.dorandoran.post.domain;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.common.baseentity.EntityStatus;
import com.grasshouse.dorandoran.member.domain.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Member author;

    @Length(max = 200, message = "글은 200자를 초과할 수 없습니다.")
    @NotBlank(message = "글의 내용은 비어 있을 수 없습니다.")
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostLike> likes = new HashSet<>();

    @NotNull
    @Embedded
    private Location location;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "depth1", column = @Column(name = "ADDRESS_DEPTH_1")),
        @AttributeOverride(name = "depth2", column = @Column(name = "ADDRESS_DEPTH_2")),
        @AttributeOverride(name = "depth3", column = @Column(name = "ADDRESS_DEPTH_3"))
    })
    private Address address;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "depth1", column = @Column(name = "AUTHOR_ADDRESS_DEPTH_1")),
        @AttributeOverride(name = "depth2", column = @Column(name = "AUTHOR_ADDRESS_DEPTH_2")),
        @AttributeOverride(name = "depth3", column = @Column(name = "AUTHOR_ADDRESS_DEPTH_3"))
    })
    private Address authorAddress;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private EntityStatus status = EntityStatus.ALIVE;

    @Builder
    public Post(Long id, Member author, String content, Location location, Address address, Address authorAddress) {
        this.id = id;
        setAuthor(author);
        this.content = content;
        this.location = location;
        this.address = address;
        this.authorAddress = authorAddress;
    }

    private void setAuthor(Member author) {
        if (Objects.isNull(this.author)) {
            this.author = author;
            this.author.addPost(this);
        }
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.delete();
    }

    public void delete() {
        this.status = EntityStatus.DELETED;
        this.comments.forEach(Comment::delete);
        this.likes.clear();
    }

    public boolean isSameAuthor(Member member) {
        return author.isSameMember(member);
    }

    public Post filterAliveComments() {
        this.comments = this.comments.stream()
            .filter(Comment::isAlive)
            .collect(Collectors.toList());
        return this;
    }
}
