package club.inq.team1.controller;

import club.inq.team1.dto.projection.Following;
import club.inq.team1.entity.Follow;
import club.inq.team1.entity.User;
import club.inq.team1.repository.UserRepository;
import club.inq.team1.service.FollowService;
import club.inq.team1.service.UserService;
import club.inq.team1.service.impl.FollowServiceImpl;
import club.inq.team1.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "FollowController", description = "팔로윙 관련 API 컨트롤러")
public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    @Autowired
    public FollowController(FollowServiceImpl followService, UserServiceImpl userService) {
        this.followService = followService;
        this.userService = userService;
    }

    // 팔로윙
    @PostMapping("/follow/{opponentId}")
    public ResponseEntity<String> follow(@PathVariable("opponentId") Long opponentId){
        long currentUserId = userService.getCurrentLoginUser()
                .orElseThrow()
                .getUserId();
        try {
            followService.follow(currentUserId, opponentId);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("해당 사용자를 팔로우 하였습니다!");  // 팔로우 성공 시 201 CREATED 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("이미 팔로우 한 사용자 입니다!");  // 이미 팔로우 관계가 존재하는 경우 400 BAD_REQUEST
        }
    }

    // 언팔로윙
    @DeleteMapping("/unfollow/{opponentId}")
    public ResponseEntity<String> unfollow(@PathVariable("opponentId") Long opponentId) {
        User currentUser = userService.getCurrentLoginUser().orElseThrow();
        Long currentUserId = currentUser.getUserId();
        try {
            followService.unfollow(currentUserId, opponentId);
            return ResponseEntity.status(HttpStatus.OK)
                .body("팔로우를 취소했습니다!");  // 언팔로우 성공 시 200 ok 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("팔로우 하지 않은 사용자입니다!");  // 팔로우 관계가 없으면 400 BAD_REQUEST
        }
    }

    //팔로워 조회
    @GetMapping("/{userId}/follower")
    public ResponseEntity<List<Follow>> findFollower(@PathVariable("userId") Long userId){
        List<Follow> followers = followService.findAllFollowers(userId);
        if (followers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 팔로워가 없으면 NOT_FOUND 반환
        }
        return ResponseEntity.ok(followers);  // 팔로워 목록 반환
    }

    //특정 팔로워 확인
    @GetMapping("/{userId}/follower/{opponentId}")
    public ResponseEntity<Boolean> findSpecificFollower(@PathVariable("userId") Long userId, @PathVariable("opponentId") Long opponentId) {
        if (followService.findSpecificFollower(userId, opponentId)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);  // 팔로우 관계가 존재하면 OK 반환
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);  // 팔로우 관계가 없으면 NOT_FOUND 반환
    }

    //팔로윙 조회
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<Following>> findFollowee(@PathVariable("userId") Long userId){
        List<Following> followees = followService.findAllFollowees(userId);
        if (followees.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());  // 빈 리스트 반환
        }
        return ResponseEntity.ok(followees);
    }

    //특정 팔로윙 확인
    @GetMapping("/{userId}/following/{opponentId}")
    public ResponseEntity<Boolean> findSpecificFollowee(@PathVariable("userId") Long currentUserId, @PathVariable("opponentId") Long opponentId){
        if (followService.findSpecificFollowee(currentUserId, opponentId)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);  // 팔로우 관계가 존재하면 OK 반환
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);  // 팔로우 관계가 없으면 NOT_FOUND 반환
    }

    //팔로워 수 조회
    @GetMapping("/{userId}/follower/count")
    public ResponseEntity<Long> countFollower(@PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(followService.countFollowers(userId));
    }

    //팔로윙 수 조회
    @GetMapping("/{userId}/following/count")
    public ResponseEntity<Long> countFollowing(@PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(followService.countFollowings(userId));
    }

    //팔로워/팔로윙수 조회 및 특정 조회 버그 수정 필요
}

