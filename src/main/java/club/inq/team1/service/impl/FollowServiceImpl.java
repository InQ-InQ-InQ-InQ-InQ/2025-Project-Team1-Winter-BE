package club.inq.team1.service.impl;

import club.inq.team1.dto.projection.FollowerDTO;
import club.inq.team1.dto.projection.FollowingDTO;
import club.inq.team1.entity.Follow;
import club.inq.team1.entity.User;
import club.inq.team1.repository.FollowRepository;
import club.inq.team1.dto.projection.FollowerUserProjectionDTO;
import club.inq.team1.repository.UserRepository;
import club.inq.team1.service.FollowService;
import club.inq.team1.util.CurrentUser;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    // 팔로우
    @Override
    @Transactional
    public boolean follow(Long opponentId) {
        User follower = currentUser.get();
        User followee = getUserOrThrow(opponentId,"해당 사용자가 존재하지 않습니다.");

        if(findSpecificFollower(follower.getUserId(), opponentId)){
            return false;
        }

        // 팔로우 관계 생성
        Follow follow = new Follow();  // `Follow` 엔티티 생성
        follow.setFollower(follower);
        follow.setFollowee(followee);
        follow.setAlarm(false);
        followRepository.save(follow);// DB에 저장

        return true;
    }

    // 언팔로우
    @Override
    public boolean unfollow(Long opponentId) {
        User follower = currentUser.get();
        User followee = getUserOrThrow(opponentId,"해당 사용자가 존재하지 않습니다.");

        if (!findSpecificFollower(follower.getUserId(),opponentId)) {
            return false;
        }

        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee).orElseThrow();

        followRepository.delete(follow);  // 팔로우 관계 삭제
        return true;
    }

    // 팔로워 조회 (전체 팔로워 목록)
    @Override
    public List<FollowerDTO> findAllFollowers(Long userId, Integer page) {
        User user = getUserOrThrow(userId,"해당 사용자가 존재하지 않습니다.");
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return followRepository.findFollowersByFollowee(user, pageRequest);  // user.get()로 User 객체를 전달
    }

    // 특정 팔로워 확인 (특정 유저가 팔로우하는지 확인)
    @Override
    public boolean findSpecificFollower(Long followerId, Long followeeId) {
        User follower = getUserOrThrow(followerId, "팔로워에 해당하는 유저가 존재하지 않습니다.");
        User followee = getUserOrThrow(followeeId, "팔로윙에 해당하는 유저가 존재하지 않습니다.");
        return followRepository.findByFollowerAndFollowee(follower, followee).isPresent();
    }

    // 팔로윙 조회 (전체 팔로윙 목록)
    public List<FollowingDTO> findAllFollowees(Long userId, Integer page) {
        User user = getUserOrThrow(userId,"해당 유저가 존재하지 않습니다.");
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return followRepository.findFolloweesByFollower(user, pageRequest);  // user.get()로 User 객체를 전달
    }

    // 특정 팔로윙 확인 (특정 유저를 팔로우하고 있는지 확인)
    public boolean findSpecificFollowee(Long followerId, Long followeeId) {
        User follower = getUserOrThrow(followerId,"현재 사용자가 존재하지 않습니다.");
        User followee = getUserOrThrow(followeeId,"해당 사용자가 존재하지 않습니다.");

        return followRepository.findByFollowerAndFollowee(follower, followee).isPresent();
    }

    // 팔로워 수 조회
    public Long countFollowers(Long userId) {
        User user = getUserOrThrow(userId, "없는 회원입니다.");
        return followRepository.countByFollowee(user);
    }

    // 팔로윙 수 조회
    public Long countFollowings(Long userId) {
        User user = getUserOrThrow(userId, "없는 회원입니다.");
        return followRepository.countByFollower(user);
    }

    // 해당 유저가 존재하는지 확인한다.
    private User getUserOrThrow(Long userId, String exceptionMessage) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException(exceptionMessage));
    }

    /**
     * 알람 설정 상태를 반대로 한다. ex) 알람 상태가 true 였다면 false 로 바꿈.
     * @param followeeId 알람 설정을 할 상대의 회원 식별 아이디를 입력한다.
     * @return 현재 알람 설정 상태를 반환한다.
     */

    @Override
    @Transactional
    public boolean setAlarm(Long followeeId){
        User user = currentUser.get();
        User followee = getUserOrThrow(followeeId, "해당 유저가 존재하지 않습니다.");
        Follow follow = followRepository.findByFollowerAndFollowee(user, followee).orElseThrow(()->new RuntimeException("팔로우 된 상태가 아닙니다."));
        follow.setAlarm(!follow.getAlarm());
        followRepository.save(follow);
        return follow.getAlarm();
    }

    @Override
    public List<User> findAllFollowerWithAlarmTrue(){
        User user = currentUser.get();
        return followRepository.findFollowersByFolloweeAndAlarmTrue(user)
                .stream()
                .map(FollowerUserProjectionDTO::getFollower)
                .toList();
    }
}
