package club.inq.team1.service.impl;

import club.inq.team1.dto.projection.FollowerDTO;
import club.inq.team1.dto.projection.FollowingDTO;
import club.inq.team1.entity.Follow;
import club.inq.team1.entity.User;
import club.inq.team1.repository.FollowRepository;
import club.inq.team1.repository.UserRepository;
import club.inq.team1.service.FollowService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우
    @Transactional
    public boolean follow(Long currentUserId, Long opponentId) {
        User follower = getUserOrThrow(currentUserId,"현재 사용자가 존재하지 않습니다.");
        User followee = getUserOrThrow(opponentId,"해당 사용자가 존재하지 않습니다.");

        if(findSpecificFollower(currentUserId, opponentId)){
            return false;
        }

        // 팔로우 관계 생성

        Follow follow = new Follow();  // `Follow` 엔티티 생성
        follow.setFollowerId(follower);
        follow.setFolloweeId(followee);
        follow.setAlarm(false);
        Follow savedFollow = followRepository.save(follow);// DB에 저장

        follower.getFollowings().add(savedFollow);
        followee.getFollowers().add(savedFollow);
        userRepository.save(follower);
        userRepository.save(followee);

        return true;
    }

    // 언팔로우
    public boolean unfollow(Long currentUserId, Long opponentId) {
        User follower = getUserOrThrow(currentUserId,"현재 사용자가 존재하지 않습니다.");
        User followee = getUserOrThrow(opponentId,"해당 사용자가 존재하지 않습니다.");

        if (!findSpecificFollower(currentUserId, opponentId)) {
            return false;
        }

        Follow follow = followRepository.findByFollowerIdAndFolloweeId(follower, followee).orElseThrow();

        followRepository.delete(follow);  // 팔로우 관계 삭제
        return true;
    }

    // 팔로워 조회 (전체 팔로워 목록)
    public List<FollowerDTO> findAllFollowers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return followRepository.findFollowersByFolloweeId(user);  // user.get()로 User 객체를 전달
    }

    // 특정 팔로워 확인 (특정 유저가 팔로우하는지 확인)
    public boolean findSpecificFollower(Long currentUserId, Long opponentId) {
        User follower = getUserOrThrow(currentUserId,"현재 사용자가 존재하지 않습니다.");
        User followee = getUserOrThrow(opponentId,"해당 사용자가 존재하지 않습니다.");

        return followRepository.findByFollowerIdAndFolloweeId(follower, followee).isPresent();
    }

    // 팔로윙 조회 (전체 팔로윙 목록)
    public List<FollowingDTO> findAllFollowees(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return followRepository.findFolloweesByFollowerId(user);  // user.get()로 User 객체를 전달
    }

    // 특정 팔로윙 확인 (특정 유저를 팔로우하고 있는지 확인)
    public boolean findSpecificFollowee(Long currentUserId, Long opponentId) {
        User follower = getUserOrThrow(currentUserId,"현재 사용자가 존재하지 않습니다.");
        User followee = getUserOrThrow(opponentId,"해당 사용자가 존재하지 않습니다.");

        return followRepository.findByFollowerIdAndFolloweeId(follower, followee).isPresent();
    }

    // 팔로워 수 조회
    public Long countFollowers(Long userId) {
        User user = getUserOrThrow(userId, "없는 회원입니다.");
        return followRepository.countByFolloweeId(user);
    }

    // 팔로윙 수 조회
    public Long countFollowings(Long userId) {
        User user = getUserOrThrow(userId, "없는 회원입니다.");
        return followRepository.countByFollowerId(user);
    }

    // 해당 유저가 존재하는지 확인한다.
    private User getUserOrThrow(Long userId, String exceptionMessage) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException(exceptionMessage));
    }
}
