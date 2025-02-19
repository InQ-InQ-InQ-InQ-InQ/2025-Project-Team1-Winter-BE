package club.inq.team1.service;

import club.inq.team1.service.user.FollowService;
import club.inq.team1.service.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void follow() {

    }
}