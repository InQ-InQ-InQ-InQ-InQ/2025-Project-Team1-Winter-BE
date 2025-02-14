package club.inq.team1.service;

import static org.junit.jupiter.api.Assertions.*;

import club.inq.team1.dto.request.UserJoinDTO;
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
    void follow() {

    }
}