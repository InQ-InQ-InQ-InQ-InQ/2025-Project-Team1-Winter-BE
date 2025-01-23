package club.inq.team1.service;

import club.inq.team1.dto.projection.FollowerDTO;
import club.inq.team1.dto.projection.FollowingDTO;
import club.inq.team1.entity.User;
import java.util.List;


public interface FollowService {
    // 팔로우
    boolean follow(Long opponentId);

    // 언팔로우
    boolean unfollow(Long opponentId);

    // 팔로워 조회 (전체 팔로워 목록)
    List<FollowerDTO> findAllFollowers(Long currentUserId, Integer page);

    // 특정 팔로워 확인 (특정 유저가 팔로우하는지 확인)
    boolean findSpecificFollower(Long followerId, Long followeeId);

    // 팔로윙 조회 (전체 팔로윙 목록)
    List<FollowingDTO> findAllFollowees(Long currentUserId, Integer page);

    // 팔로워 수 조회
    Long countFollowers(Long currentUserId);

    // 팔로윙 수 조회
    Long countFollowings(Long currentUserId);

    boolean setAlarm(Long followeeId);

    List<User> findAllFollowerWithAlarmTrue();
}
