package com.grasshouse.dorandoran.post.domain;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.member.domain.Member;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member author;

    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "POST_LIKE_ID")
    private Set<PostLike> likes = new HashSet<>();

    @Embedded
    private Location location;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "depth1", column = @Column(name = "ADDRESS_DEPTH_1")),
        @AttributeOverride(name = "depth2", column = @Column(name = "ADDRESS_DEPTH_2")),
        @AttributeOverride(name = "depth3", column = @Column(name = "ADDRESS_DEPTH_3"))
    })
    private Address address;
}
