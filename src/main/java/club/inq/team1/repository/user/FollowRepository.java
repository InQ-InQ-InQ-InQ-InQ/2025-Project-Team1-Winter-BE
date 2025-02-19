package club.inq.team1.repository.user;

import club.inq.team1.entity.Follow;
import club.inq.team1.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Slice<Follow> findByFollower(User follower, Pageable pageable);
    Slice<Follow> findByFollowee(User followee, Pageable pageable);
    Optional<Follow> findByFollowerAndFollowee(User follower, User followee);
    long countByFollowee(User followee);
    long countByFollower(User follower);

    // 팔로우 관계 삭제 (팔로우하는 사람(followerId)과 팔로우되는 사람(followeeId) 기준)
    void delete(Follow follow);  // JpaRepository에 있는 기본 delete 메소드 활용

    List<Follow> findByFolloweeAndAlarmTrue(User followee);
}
