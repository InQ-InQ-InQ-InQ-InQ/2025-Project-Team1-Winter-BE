package club.inq.team1.service;

import club.inq.team1.entity.Follow;
import java.util.List;


public interface FollowService {
    // 팔로우
    boolean follow(Long currentUserId, Long opponentId);

    // 언팔로우
    boolean unfollow(Long currentUserId, Long opponentId);

    // 팔로워 조회 (전체 팔로워 목록)
    List<Follow> findAllFollowers(Long currentUserId);

    // 특정 팔로워 확인 (특정 유저가 팔로우하는지 확인)
    boolean findSpecificFollower(Long currentUserId, Long opponentId);

    // 팔로윙 조회 (전체 팔로윙 목록)
    List<Follow> findAllFollowees(Long currentUserId);

    // 특정 팔로윙 확인 (특정 유저를 팔로우하고 있는지 확인)
    boolean findSpecificFollowee(Long currentUserId, Long opponentId);

    // 팔로워 수 조회
    int countFollowers(Long currentUserId);

    // 팔로윙 수 조회
    int countFollowings(Long currentUserId);

}
