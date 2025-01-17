package club.inq.team1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User followerId;

    @ManyToOne
    @JoinColumn(name = "followee_id", nullable = false)
    private User followeeId;

    @Column(name = "alarm", nullable = false)
    private Boolean alarm = false;

    public Follow(User followerId, User followeeId) {
        this.followerId = followerId;  // followerId로 User 객체 생성
        this.followeeId = followeeId;  // followeeId로 User 객체 생성
    }
}
