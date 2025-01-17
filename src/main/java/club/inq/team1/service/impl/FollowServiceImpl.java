package club.inq.team1.service.impl;

import club.inq.team1.entity.Follow;
import club.inq.team1.entity.User;
import club.inq.team1.repository.FollowRepository;
import club.inq.team1.repository.UserRepository;
import club.inq.team1.service.FollowService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    //팔로우
    public ResponseEntity<?> follow(Long currentUserId, Long opponentId) {
        Optional<User> currentUser = userRepository.findById(currentUserId);
        Optional<User> opponentUser = userRepository.findById(opponentId);
        if (!followRepository.findByFollowerIdAndFolloweeId(currentUser.get(), opponentUser.get()).isEmpty()) {
            throw new IllegalArgumentException("이미 팔로우 했습니다!");
        }

        // 팔로우 관계 생성
        User follower = userRepository.findById(currentUserId)
            .orElseThrow(() -> new IllegalArgumentException("현재 사용자가 존재하지 않습니다."));
        User followee = userRepository.findById(opponentId)
            .orElseThrow(() -> new IllegalArgumentException("팔로우 할 사용자가 존재하지 않습니다."));

        // 팔로우 관계 생성
        Follow follow = new Follow(follower, followee);  // `Follow` 엔티티 생성
        followRepository.save(follow);  // DB에 저장

        return new ResponseEntity<>("팔로우 성공", HttpStatus.CREATED);
    }

    //언팔로우
    public boolean unfollow(Long currentUserId, Long opponentId) {
        // 팔로우 관계를 찾고 삭제
        Optional<User> currentUser = userRepository.findById(currentUserId);
        Optional<User> opponentUser = userRepository.findById(opponentId);
        List<Follow> follow = followRepository.findByFollowerIdAndFolloweeId(currentUser.get(), opponentUser.get());
        if (!follow.isEmpty()) {
            followRepository.delete(follow.get(0));  // 팔로우 관계 삭제
            return true;
        } else {
            throw new IllegalArgumentException("팔로우 하지 않았습니다!");
        }
    }

    // 팔로워 조회 (전체 팔로워 목록)
    public List<Follow> findAllFollowers(Long currentUserId) {
        Optional<User> currentUser = userRepository.findById(currentUserId);
        if (currentUser.isPresent()) {
            return followRepository.findFollowersByFolloweeId(currentUser.get());  // currentUser.get()로 User 객체를 전달
        } else {
            return Collections.emptyList();  // null 대신 빈 리스트 반환
        }
    }

    // 특정 팔로워 확인 (특정 유저가 팔로우하는지 확인)
    public List<Follow> findSpecificFollower(Long currentUserId, Long opponentId) {
        Optional<User> currentUser = userRepository.findById(currentUserId);
        Optional<User> opponentUser = userRepository.findById(opponentId);
        if (currentUser.isPresent() && opponentUser.isPresent()) {
            return followRepository.findByFollowerIdAndFolloweeId(opponentUser.get(), currentUser.get());  // 팔로우하는 사람(followerId), 팔로우되는 사람(followeeId)
        }
        else{
            return Collections.emptyList();  // null 대신 빈 리스트 반환
        }
    }

    // 팔로윙 조회 (전체 팔로윙 목록)
    public List<Follow> findAllFollowees(Long currentUserId) {
        Optional<User> currentUser = userRepository.findById(currentUserId);
        if (currentUser.isPresent()) {
            return followRepository.findFolloweesByFollowerId(currentUser.get());  // currentUser.get()로 User 객체를 전달
        }
        return Collections.emptyList();  // null 대신 빈 리스트 반환
    }

    // 특정 팔로윙 확인 (특정 유저를 팔로우하고 있는지 확인)
    public List<Follow> findSpecificFollowee(Long currentUserId, Long opponentId) {
        Optional<User> currentUser = userRepository.findById(currentUserId);
        Optional<User> opponentUser = userRepository.findById(opponentId);
        if (currentUser.isPresent() && opponentUser.isPresent()) {
            return followRepository.findByFollowerIdAndFolloweeId(currentUser.get(), opponentUser.get());  // 팔로우하는 사람(followerId), 팔로우되는 사람(followeeId)
        }
        else{
            return Collections.emptyList();
        }
    }

    //팔로워 수 조회
    public int countFollowers(Long currentUserId) {
        List<Follow> followers = findAllFollowers(currentUserId);
        return followers.size();
    }

    //팔로윙 수 조회
    public int countFollowings(Long currentUserId) {
        List<Follow> followings = findAllFollowees(currentUserId);
        return followings.size();
    }
}
