package club.inq.team1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reply_like")
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_like_id")
    private Long replyLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
