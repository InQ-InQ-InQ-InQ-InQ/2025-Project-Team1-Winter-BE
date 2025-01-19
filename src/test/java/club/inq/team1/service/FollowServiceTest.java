package club.inq.team1.service;

import static org.junit.jupiter.api.Assertions.*;

import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.dto.projection.FollowerDTO;
import club.inq.team1.dto.projection.FollowingDTO;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
class FollowServiceTest {
    private final FollowService followService;
    private final UserService userService;

    @Autowired
    FollowServiceTest(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @Test
    @DisplayName("팔로우 성공 테스트")
    @Rollback
    void follow() {
        Long userId = userService.acceptUser(new UserJoinDTO("hello", "1234", "qwer")).getUserId();
        Long userId1 = userService.acceptUser(new UserJoinDTO("hello1", "1234", "qwer1")).getUserId();

        boolean follow = followService.follow(userId, userId1);
        assertTrue(follow);
    }

    @Test
    @DisplayName("팔로우 실패 테스트 없는 유저")
    @Rollback
    void follow1() {
        Long userId = userService.acceptUser(new UserJoinDTO("hello", "1234", "qwer")).getUserId();
        Long userId1 = userService.acceptUser(new UserJoinDTO("hello1", "1234", "qwer1")).getUserId();

        assertThrows(RuntimeException.class,()-> followService.follow(userId,0L));
    }

    @Test
    @DisplayName("팔로우 실패 로그인 안된 상태")
    @Rollback
    void follow2() {
        userService.acceptUser(new UserJoinDTO("hello","1234","qwer"));
        userService.acceptUser(new UserJoinDTO("hello1","1234","qwer1"));

        assertThrows(RuntimeException.class,()-> followService.follow(null,3L));
    }

    @Test
    @DisplayName("팔로우 후 언팔로우 성공")
    @Rollback
    void unfollow() {
        Long userId = userService.acceptUser(new UserJoinDTO("hello", "1234", "qwer")).getUserId();
        Long userId1 = userService.acceptUser(new UserJoinDTO("hello1", "1234", "qwer1")).getUserId();

        boolean follow = followService.follow(userId, userId1);
        assertTrue(follow);
        boolean unfollow = followService.unfollow(userId, userId1);
        assertTrue(unfollow);
    }

    @Test
    @DisplayName("팔로우가 안된 상태에서 언팔로우 시도")
    @Rollback
    void unfollow1() {
        Long userId = userService.acceptUser(new UserJoinDTO("hello", "1234", "qwer")).getUserId();
        Long userId1 = userService.acceptUser(new UserJoinDTO("hello1", "1234", "qwer1")).getUserId();

        boolean unfollow = followService.unfollow(userId, userId1);
        assertFalse(unfollow);
    }

    @Test
    @DisplayName("모든 팔로워 확인")
    @Rollback
    void findAllFollowers() {
        Long userId = userService.acceptUser(new UserJoinDTO("hello", "1234", "qwer")).getUserId();
        Long userId1 = userService.acceptUser(new UserJoinDTO("hello1", "1234", "qwer1")).getUserId();
        Long userId2 = userService.acceptUser(new UserJoinDTO("hello2", "1234", "qwer2")).getUserId();
        Long userId3 = userService.acceptUser(new UserJoinDTO("hello3", "1234", "qwer3")).getUserId();

        followService.follow(userId,userId3);
        followService.follow(userId1,userId3);
        followService.follow(userId2,userId3);

        List<FollowerDTO> allFollowers = followService.findAllFollowers(userId3, 1);
        assertEquals("qwer",allFollowers.get(0).getFollower().getUserInfo().getNickname());
        assertEquals("qwer1",allFollowers.get(1).getFollower().getUserInfo().getNickname());
        assertEquals("qwer2",allFollowers.get(2).getFollower().getUserInfo().getNickname());
    }

    @Test
    @DisplayName("팔로우 안한 상태에서 확인")
    @Rollback
    void findSpecificFollower() {
        Long userId = userService.acceptUser(new UserJoinDTO("hello", "1234", "qwer")).getUserId();
        Long userId1 = userService.acceptUser(new UserJoinDTO("hello1", "1234", "qwer1")).getUserId();

        boolean specificFollower = followService.findSpecificFollower(userId, userId1);
        assertFalse(specificFollower);
    }

    @Test
    @DisplayName("모든 팔로윙 확인")
    @Rollback
    void findAllFollowees() {
        Long userId = userService.acceptUser(new UserJoinDTO("hello", "1234", "qwer")).getUserId();
        Long userId1 = userService.acceptUser(new UserJoinDTO("hello1", "1234", "qwer1")).getUserId();
        Long userId2 = userService.acceptUser(new UserJoinDTO("hello2", "1234", "qwer2")).getUserId();
        Long userId3 = userService.acceptUser(new UserJoinDTO("hello3", "1234", "qwer3")).getUserId();

        followService.follow(userId,userId1);
        followService.follow(userId,userId2);
        followService.follow(userId,userId3);

        List<FollowingDTO> allFollowees = followService.findAllFollowees(userId, 1);
        assertEquals("qwer1",allFollowees.get(0).getFollowee().getUserInfo().getNickname());
        assertEquals("qwer2",allFollowees.get(1).getFollowee().getUserInfo().getNickname());
        assertEquals("qwer3",allFollowees.get(2).getFollowee().getUserInfo().getNickname());
    }

    @Test
    @Disabled
    void findSpecificFollowee() {

    }

    @Test
    @DisplayName("팔로워 숫자 파악 성공")
    @Rollback
    void countFollowers() {
        Long userId = userService.acceptUser(new UserJoinDTO("hello", "1234", "qwer")).getUserId();
        Long userId1 = userService.acceptUser(new UserJoinDTO("hello1", "1234", "qwer1")).getUserId();
        Long userId2 = userService.acceptUser(new UserJoinDTO("hello2", "1234", "qwer2")).getUserId();
        Long userId3 = userService.acceptUser(new UserJoinDTO("hello3", "1234", "qwer3")).getUserId();

        followService.follow(userId,userId3);
        followService.follow(userId1,userId3);
        followService.follow(userId2,userId3);

        Long followers = followService.countFollowers(userId3);
        assertEquals(3,followers);
    }

    @Test
    @DisplayName("팔로윙 숫자 파악 성공")
    @Rollback
    void countFollowings() {
        Long userId = userService.acceptUser(new UserJoinDTO("hello", "1234", "qwer")).getUserId();
        Long userId1 = userService.acceptUser(new UserJoinDTO("hello1", "1234", "qwer1")).getUserId();
        Long userId2 = userService.acceptUser(new UserJoinDTO("hello2", "1234", "qwer2")).getUserId();
        Long userId3 = userService.acceptUser(new UserJoinDTO("hello3", "1234", "qwer3")).getUserId();

        followService.follow(userId,userId1);
        followService.follow(userId,userId2);
        followService.follow(userId,userId3);

        Long followings = followService.countFollowings(userId);
        assertEquals(3,followings);
    }
}