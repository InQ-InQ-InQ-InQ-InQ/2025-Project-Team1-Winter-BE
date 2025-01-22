package club.inq.team1.controller;

import club.inq.team1.dto.projection.FollowerDTO;
import club.inq.team1.dto.projection.FollowingDTO;
import club.inq.team1.dto.response.ResponseFollowDTO;
import club.inq.team1.service.FollowService;
import club.inq.team1.service.UserService;
import club.inq.team1.service.impl.FollowServiceImpl;
import club.inq.team1.service.impl.UserServiceImpl;
import club.inq.team1.util.mapper.FollowMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<String> follow(@PathVariable("opponentId") Long opponentId) {
        long currentUserId = userService.getCurrentLoginUser()
                .orElseThrow()
                .getUserId();
        if(Objects.equals(currentUserId, opponentId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("자기 자신을 팔로우 할 수 없습니다!");
        }
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
        Long currentUserId = userService.getCurrentLoginUser()
                .orElseThrow()
                .getUserId();
        if(Objects.equals(currentUserId, opponentId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("자기 자신을 언팔로우 할 수 없습니다!");
        }
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
    @GetMapping(value = "/{userId}/follower", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseFollowDTO>> findFollower(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) {
            page = 1;
        }
        List<ResponseFollowDTO> followers = followService.findAllFollowers(userId, page).stream()
                .map(FollowMapper::toResponseFollowDTO).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(followers);  // 팔로워 목록 반환
    }

    //특정 팔로워 확인
    @GetMapping("/{userId}/follower/{opponentId}")
    public ResponseEntity<Boolean> findSpecificFollower(
            @PathVariable("userId") Long userId,
            @PathVariable("opponentId") Long opponentId) {
        if (followService.findSpecificFollower(userId, opponentId)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(true);  // 팔로우 관계가 존재하면 OK 반환
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(false);  // 팔로우 관계가 없으면 NOT_FOUND 반환
    }

    //팔로윙 조회
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<ResponseFollowDTO>> findFollowee(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) {
            page = 1;
        }
        List<ResponseFollowDTO> followees = followService.findAllFollowees(userId, page).stream()
                .map(FollowMapper::toResponseFollowDTO).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(followees);
    }

    //특정 팔로윙 확인
    @GetMapping("/{userId}/following/{opponentId}")
    public ResponseEntity<Boolean> findSpecificFollowee(
            @PathVariable("userId") Long currentUserId,
            @PathVariable("opponentId") Long opponentId) {
        if (followService.findSpecificFollowee(currentUserId, opponentId)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(true);  // 팔로우 관계가 존재하면 OK 반환
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(false);  // 팔로우 관계가 없으면 NOT_FOUND 반환
    }

    //팔로워 수 조회
    @GetMapping("/{userId}/follower/count")
    public ResponseEntity<Long> countFollower(@PathVariable("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(followService.countFollowers(userId));
    }

    //팔로윙 수 조회
    @GetMapping("/{userId}/following/count")
    public ResponseEntity<Long> countFollowing(@PathVariable("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(followService.countFollowings(userId));
    }

    //팔로워/팔로윙수 조회 및 특정 조회 버그 수정 필요
}

