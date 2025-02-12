package club.inq.team1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
@BatchSize(size = 100)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false, name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp //댓글이 수정될 때 자동으로 갱신됨
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    
    @OneToMany(mappedBy = "comment", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CommentLike> commentLikes = new ArrayList<>();
    
    @OneToMany(mappedBy = "comment", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reply> replies = new ArrayList<>();
}
