package club.inq.team1.controller;

import club.inq.team1.entity.Follow;
import club.inq.team1.entity.User;
import club.inq.team1.repository.UserRepository;
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
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@RestController
@RequestMapping("/api/users")
@Tag(name = "FollowController", description = "팔로윙 관련 API 컨트롤러")
public class FollowController {
    private final FollowServiceImpl followService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public FollowController(FollowServiceImpl followService, UserRepository userRepository,
        UserServiceImpl userServiceImpl) {
        this.followService = followService;
        this.userServiceImpl = userServiceImpl;
    }

    //팔로윙
    @PostMapping("/follow/{opponentId}")
    private ResponseEntity<?> follow(@PathVariable Long opponentId){
        Optional<User> currentUser = userServiceImpl.getCurrentLoginUser();
        Long currentUserId = currentUser.get().getUserId();
        try {
            followService.follow(currentUserId, opponentId);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("해당 사용자를 팔로우 하였습니다!");  // 팔로우 성공 시 201 CREATED 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("이미 팔로우 한 사용자 입니다!");  // 이미 팔로우 관계가 존재하는 경우 400 BAD_REQUEST
        }
    }

    //언팔로윙
    @DeleteMapping("/unfollow/{opponentId}")
    private ResponseEntity<?> unfollow(@PathVariable Long opponentId) {
        Optional<User> currentUser = userServiceImpl.getCurrentLoginUser();
        Long currentUserId = currentUser.get().getUserId();
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
    @GetMapping("/{currentUserId}/follower")
    private ResponseEntity<?> findFollower(@PathVariable Long currentUserId){
        List<Follow> followers = followService.findAllFollowers(currentUserId);
        if (followers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 팔로워가 없으면 NOT_FOUND 반환
        }
        return ResponseEntity.ok(followers);  // 팔로워 목록 반환
    }

    //특정 팔로워 확인
    @GetMapping("/{currentUserId}/follower/{opponentId}")
    public ResponseEntity<?> findSpecificFollower(@PathVariable Long currentUserId, @PathVariable Long opponentId) {
        List<Follow> result = followService.findSpecificFollower(currentUserId, opponentId);
        if (!result.isEmpty()) {
            return new ResponseEntity<>(true, HttpStatus.OK);  // 팔로우 관계가 존재하면 OK 반환
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);  // 팔로우 관계가 없으면 NOT_FOUND 반환
    }

    //팔로윙 조회
    @GetMapping("/{currentUserId}/following")
    private ResponseEntity<?> findFollowee(@PathVariable Long currentUserId){
        List<Follow> followees = followService.findAllFollowees(currentUserId);
        if (followees.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());  // 빈 리스트 반환
        }
        return ResponseEntity.ok(followees);
    }

    //특정 팔로윙 확인
    @GetMapping("/{currentUserId}/following/{opponentId}")
    private ResponseEntity<?> findSpecificFollowee(@PathVariable Long currentUserId, @PathVariable Long opponentId){
        List<Follow> result = followService.findSpecificFollowee(currentUserId, opponentId);
        if (!result.isEmpty()) {
            return new ResponseEntity<>(true, HttpStatus.OK);  // 팔로우 관계가 존재하면 OK 반환
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);  // 팔로우 관계가 없으면 NOT_FOUND 반환
    }

    //팔로워 수 조회
    @GetMapping("/{currentUserId}/follower/count")
    private ResponseEntity<?>  countFollower(@PathVariable Long currentUserId){
        return ResponseEntity.status(HttpStatus.OK).body(followService.countFollowers(currentUserId));
    }

    //팔로윙 수 조회
    @GetMapping("/{currentUserId}/following/count")
    private ResponseEntity<?> countFollowing(@PathVariable Long currentUserId){
        return ResponseEntity.status(HttpStatus.OK).body(followService.countFollowings(currentUserId));
    }

    //팔로워/팔로윙수 조회 및 특정 조회 버그 수정 필요
}

