package club.inq.team1.service.impl;

import club.inq.team1.entity.Follow;
import club.inq.team1.entity.User;
import club.inq.team1.repository.FollowRepository;
import club.inq.team1.repository.UserRepository;
import club.inq.team1.service.FollowService;
import java.util.ArrayList;
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
    public boolean follow(Long currentUserId, Long opponentId) {
        List<User> list = checkUsers(currentUserId, opponentId);
        User follower = list.get(0);
        User followee = list.get(1);

        if(findSpecificFollower(currentUserId, opponentId)){
            throw new IllegalArgumentException("이미 팔로우 했습니다!");
        }

        // 팔로우 관계 생성
        Follow follow = new Follow(follower, followee);  // `Follow` 엔티티 생성
        followRepository.save(follow);  // DB에 저장

        return true;
    }

    //언팔로우
    public boolean unfollow(Long currentUserId, Long opponentId) {
        List<User> list = checkUsers(currentUserId, opponentId);
        User follower = list.get(0);
        User followee = list.get(1);

        if (!findSpecificFollower(currentUserId, opponentId)) {
            throw new IllegalArgumentException("팔로우 하지 않았습니다!");
        }

        Optional<Follow> follow =
            followRepository.findByFollowerIdAndFolloweeId(follower, followee);

        followRepository.delete(follow.get());  // 팔로우 관계 삭제
        return true;
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
    public boolean findSpecificFollower(Long currentUserId, Long opponentId) {
        List<User> list = checkUsers(currentUserId, opponentId);
        User follower = list.get(1);
        User followee = list.get(0);

        return followRepository.findByFollowerIdAndFolloweeId(follower, followee).isPresent();
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
    public boolean findSpecificFollowee(Long currentUserId, Long opponentId) {
        List<User> list = checkUsers(currentUserId, opponentId);
        User follower = list.get(0);
        User followee = list.get(1);

        return followRepository.findByFollowerIdAndFolloweeId(follower, followee).isPresent();
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

    //해당 유저들이 존재하는지 확인합니다.
    private List<User> checkUsers(Long currentUserId, Long opponentUserId) {
        Optional<User> currentUser = userRepository.findById(currentUserId);
        Optional<User> opponentUser = userRepository.findById(opponentUserId);

        List<User> list = new ArrayList<>();
        list.add(currentUser.orElseThrow(() ->
            new IllegalArgumentException("현재 사용자가 존재하지 않습니다.")));
        list.add(opponentUser.orElseThrow(() ->
            new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")));

        return list;
    }
}
