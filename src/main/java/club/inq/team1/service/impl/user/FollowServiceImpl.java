package club.inq.team1.service.impl.user;

import club.inq.team1.dto.response.user.ResponseUserPrivateInfoDTO;
import club.inq.team1.entity.Follow;
import club.inq.team1.entity.Mail;
import club.inq.team1.entity.User;
import club.inq.team1.repository.user.FollowRepository;
import club.inq.team1.repository.user.MailRepository;
import club.inq.team1.repository.user.UserRepository;
import club.inq.team1.service.user.FollowService;
import club.inq.team1.service.user.UserService;
import club.inq.team1.util.CurrentUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;
    private final UserService userService;
    private final CurrentUser currentUser;

    // 팔로우
    @Override
    @Transactional
    public boolean follow(Long opponentId) {
        User follower = currentUser.get();
        User followee = userService.getUserOrThrow(opponentId);

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
        User followee = userService.getUserOrThrow(opponentId);

        if (!findSpecificFollower(follower.getUserId(),opponentId)) {
            return false;
        }

        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee).orElseThrow();

        followRepository.delete(follow);  // 팔로우 관계 삭제
        return true;
    }

    // 팔로워 조회 (전체 팔로워 목록)
    @Override
    @Transactional
    public Slice<ResponseUserPrivateInfoDTO> findAllFollowers(Long userId, Pageable pageable){
        User user = userService.getUserOrThrow(userId);
        return followRepository.findByFollowee(user,pageable)
                .map(Follow::getFollower)
                .map(userService::toResponseUserPrivateInfoDTO);
    }

    // 특정 팔로워 확인 (특정 유저가 팔로우하는지 확인)
    @Override
    public boolean findSpecificFollower(Long followerId, Long followeeId) {
        User follower = userService.getUserOrThrow(followerId);
        User followee = userService.getUserOrThrow(followeeId);
        return followRepository.findByFollowerAndFollowee(follower, followee).isPresent();
    }

    // 팔로윙 조회 (전체 팔로윙 목록)
    @Override
    @Transactional
    public Slice<ResponseUserPrivateInfoDTO> findAllFollowees(Long userId, Pageable pageable){
        User user = userService.getUserOrThrow(userId);
        return followRepository.findByFollower(user,pageable)
                .map(Follow::getFollowee)
                .map(userService::toResponseUserPrivateInfoDTO);
    }

    // 특정 팔로윙 확인 (특정 유저를 팔로우하고 있는지 확인)
    public boolean findSpecificFollowee(Long followerId, Long followeeId) {
        User follower = userService.getUserOrThrow(followerId);
        User followee = userService.getUserOrThrow(followeeId);

        return followRepository.findByFollowerAndFollowee(follower, followee).isPresent();
    }

    // 팔로워 수 조회
    @Override
    public Long countFollowers(Long userId) {
        User user = userService.getUserOrThrow(userId);
        return followRepository.countByFollowee(user);
    }

    // 팔로윙 수 조회
    @Override
    public Long countFollowings(Long userId) {
        User user = userService.getUserOrThrow(userId);
        return followRepository.countByFollower(user);
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
        User followee = userService.getUserOrThrow(followeeId);
        Follow follow = followRepository.findByFollowerAndFollowee(user, followee).orElseThrow(()->new RuntimeException("팔로우 된 상태가 아닙니다."));
        follow.setAlarm(!follow.getAlarm());
        followRepository.save(follow);
        return follow.getAlarm();
    }

    private List<User> findAllFollowerWithAlarmTrue(){
        User user = currentUser.get();
        return followRepository.findByFolloweeAndAlarmTrue(user)
                .stream()
                .map(Follow::getFollower)
                .toList();
    }

    @Transactional
    public boolean sendAlarm(){
        List<User> users = findAllFollowerWithAlarmTrue();
        users.stream()
                .map(user->Mail.builder()
                        .user(user)
                        // todo : post 도 설정하도록 바꿔야됨.
                        .build())
                .forEach(mailRepository::save);
        return true;
    }
}
