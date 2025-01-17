package club.inq.team1.repository;

import club.inq.team1.entity.Follow;
import club.inq.team1.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findFolloweesByFollowerId(User currentUser);
    List<Follow> findFollowersByFolloweeId(User currentUser);
    Optional<Follow> findByFollowerIdAndFolloweeId(User opponentUser, User currentUser);

    // 팔로우 관계 삭제 (팔로우하는 사람(followerId)과 팔로우되는 사람(followeeId) 기준)
    void delete(Follow follow);  // JpaRepository에 있는 기본 delete 메소드 활용

}
