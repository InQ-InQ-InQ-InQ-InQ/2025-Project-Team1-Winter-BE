package club.inq.team1.controller;

import club.inq.team1.dto.response.user.ResponseUserPrivateInfoDTO;
import club.inq.team1.service.FollowService;
import club.inq.team1.util.CurrentUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "FollowController", description = "팔로윙 관련 API 컨트롤러")
public class FollowController {
    private final FollowService followService;
    private final CurrentUser currentUser;
    // 팔로윙
    @PostMapping("/follow/{opponentId}")
    public ResponseEntity<String> follow(@PathVariable("opponentId") Long opponentId) {
        if (currentUser.get().getUserId().equals(opponentId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("자기 자신을 팔로우 할 수 없습니다!");
        }
        if(followService.follow(opponentId)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("해당 사용자를 팔로우 하였습니다!");  // 팔로우 성공 시 201 CREATED 응답
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("이미 팔로우 한 사용자 입니다!");  // 이미 팔로우 관계가 존재하는 경우 400 BAD_REQUEST
    }

    // 언팔로윙
    @DeleteMapping("/unfollow/{opponentId}")
    public ResponseEntity<String> unfollow(@PathVariable("opponentId") Long opponentId) {
        if (currentUser.get().getUserId().equals(opponentId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("자기 자신을 언팔로우 할 수 없습니다!");
        }
        if(followService.unfollow(opponentId)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("팔로우를 취소했습니다!");  // 언팔로우 성공 시 200 ok 응답
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("팔로우 하지 않은 사용자입니다!");  // 팔로우 관계가 없으면 400 BAD_REQUEST
    }

    //팔로워 조회
    @GetMapping(value = "/{userId}/follower", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Slice<ResponseUserPrivateInfoDTO>> findFollower(
            @PathVariable("userId") Long userId,
            @PageableDefault(size = 20, sort = {"followId"}, direction = Direction.DESC) Pageable pageable) {
        Slice<ResponseUserPrivateInfoDTO> followers = followService.findAllFollowers(userId, pageable);

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
    public ResponseEntity<Slice<ResponseUserPrivateInfoDTO>> findFollowee(
            @PathVariable("userId") Long userId,
            @PageableDefault(size = 20, sort = {"followId"}, direction = Direction.DESC) Pageable pageable) {
        Slice<ResponseUserPrivateInfoDTO> followees = followService.findAllFollowees(userId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(followees);
    }

    //특정 팔로윙 확인
    @GetMapping("/{userId}/following/{opponentId}")
    public ResponseEntity<Boolean> findSpecificFollowee(
            @PathVariable("userId") Long currentUserId,
            @PathVariable("opponentId") Long opponentId) {
        if (followService.findSpecificFollower(opponentId,currentUserId)) {
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

    /**
     * 팔로윙 한 상대의 알람 설정 상태를 기존에 설정된 것의 반대로 한다.
     * @param userId 알람 설정을 할 상대방의 아이디.
     * @return 바꾼 뒤 알람 상태를 반환한다.
     */
    @PostMapping(value = "/alarm/{userId}")
    public ResponseEntity<String> setAlarmReverse(@PathVariable("userId") Long userId){
        boolean alarm = followService.setAlarm(userId);
        return ResponseEntity.status(200)
                .body(Boolean.toString(alarm));
    }
}

