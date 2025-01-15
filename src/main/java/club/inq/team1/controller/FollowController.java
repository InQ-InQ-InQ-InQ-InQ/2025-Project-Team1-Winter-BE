package club.inq.team1.controller;

import club.inq.team1.entity.Follow;
import club.inq.team1.entity.User;
import club.inq.team1.service.impl.FollowServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final FollowServiceImpl followService;

    @Autowired
    public FollowController(FollowServiceImpl followService) {
        this.followService = followService;
    }

    //팔로윙
    @PostMapping("/{currentUserId}/follow/{opponentId}")
    private ResponseEntity<?> follow(@PathVariable Long currentUserId, @PathVariable Long opponentId){
        try {
            followService.follow(currentUserId, opponentId);
            return new ResponseEntity<>(HttpStatus.CREATED);  // 팔로우 성공 시 201 CREATED 응답
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  // 이미 팔로우 관계가 존재하는 경우 400 BAD_REQUEST
        }
    }

    //언팔로윙
    @DeleteMapping("/{currentUserId}/unfollow/{opponentId}")
    private ResponseEntity<?> unfollow(@PathVariable Long currentUserId, @PathVariable Long opponentId) {
        try {
            followService.unfollow(currentUserId, opponentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 언팔로우 성공 시 204 NO_CONTENT 응답
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(),
                HttpStatus.BAD_REQUEST);  // 팔로우 관계가 없으면 400 BAD_REQUEST
        }
    }

    //팔로워 조회
    @GetMapping("/{currentUserId}/follwer")
    private ResponseEntity<?> findFollower(@PathVariable Long currentUserId){
        List<Long> followers = followService.findAllFollowers(currentUserId);
        if (followers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 팔로워가 없으면 NOT_FOUND 반환
        }
        return new ResponseEntity<>(followers, HttpStatus.OK);  // 팔로워 목록 반환
    }

    //특정 팔로워 확인
    @GetMapping("/{currentUserId}/follower/{opponentId}")
    public ResponseEntity<?> findSpecificFollower(@PathVariable Long currentUserId, @PathVariable Long opponentId) {
        Optional<Follow> result = followService.findSpecificFollower(currentUserId, opponentId);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);  // 팔로우 관계가 존재하면 OK 반환
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 팔로우 관계가 없으면 NOT_FOUND 반환
    }

    //팔로윙 조회
    @GetMapping("/{currentUserId}/following")
    private ResponseEntity<?> findFollowee(@PathVariable Long currentUserId){
        List<Long> followees = followService.findAllFollowees(currentUserId);
        if (followees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 팔로워가 없으면 NOT_FOUND 반환
        }
        return new ResponseEntity<>(followees, HttpStatus.OK);  // 팔로워 목록 반환
    }

    //특정 팔로윙 확인
    @GetMapping("/{currentUserId}/following/{opponentId}")
    private ResponseEntity<?> findSpecificFollowee(@PathVariable Long currentUserId, @PathVariable Long opponentId){
        Optional<Follow> result = followService.findSpecificFollowee(currentUserId, opponentId);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);  // 팔로우 관계가 존재하면 OK 반환
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 팔로우 관계가 없으면 NOT_FOUND 반환
    }

    //팔로워 수 조회
    @GetMapping("/{currentUserId}/follower/count")
    private ResponseEntity<?>  countFollower(@PathVariable Long currentUserId){
        List<Long> followers = followService.findAllFollowees(currentUserId);
        return new ResponseEntity<>(followers.size(), HttpStatus.OK);
    }

    //팔로윙 수 조회
    @GetMapping("/{currentUserId}/following/count")
    private ResponseEntity<?> countFollowee(@PathVariable Long currentUserId){
        List<Long> followees = followService.findAllFollowees(currentUserId);
        return new ResponseEntity<>(followees.size(), HttpStatus.OK);
    }
    //팔로워/팔로윙수 조회 및 특정 조회 버그 수정 필요
}

