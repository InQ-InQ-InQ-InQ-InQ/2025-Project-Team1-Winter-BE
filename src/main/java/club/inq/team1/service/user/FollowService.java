package club.inq.team1.service.user;

import club.inq.team1.dto.response.user.ResponseUserPrivateInfoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface FollowService {
    // 팔로우
    boolean follow(Long opponentId);

    // 언팔로우
    boolean unfollow(Long opponentId);

    // 팔로워 조회 (전체 팔로워 목록)
    Slice<ResponseUserPrivateInfoDTO> findAllFollowers(Long userId, Pageable pageable);

    // 특정 팔로워 확인 (특정 유저가 팔로우하는지 확인)
    boolean findSpecificFollower(Long followerId, Long followeeId);

    // 팔로윙 조회 (전체 팔로윙 목록)
    Slice<ResponseUserPrivateInfoDTO> findAllFollowees(Long userId, Pageable pageable);

    // 팔로워 수 조회
    Long countFollowers(Long currentUserId);

    // 팔로윙 수 조회
    Long countFollowings(Long currentUserId);

    boolean setAlarm(Long followeeId);
}
