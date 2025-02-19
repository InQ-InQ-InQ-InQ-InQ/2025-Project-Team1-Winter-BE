package club.inq.team1.service.impl;

import club.inq.team1.service.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("UserInfo에서 User Entity 참조 테스트")
    @Disabled
    void createUser1(){

    }
}