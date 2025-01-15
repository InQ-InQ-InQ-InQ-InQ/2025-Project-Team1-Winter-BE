package club.inq.team1.controller;

import static org.junit.jupiter.api.Assertions.*;

import club.inq.team1.dto.UserJoinDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {
    private final UserController userController;
    @Autowired
    UserControllerTest(UserController userController) {
        this.userController = userController;
    }

    @Test
    @Transactional
    @DisplayName("중복된 아이디 확인 테스트")
    void existsUsername() {
        userController.join(new UserJoinDTO("string","string","nick"));
        assertEquals(false, userController.existsUsername("nick").getBody());
        assertEquals(true,userController.existsUsername("string").getBody());
    }

    @Test
    @Transactional
    @DisplayName("중복된 닉네임 확인 테스트")
    void existsNickname() {
        userController.join(new UserJoinDTO("string","string","nick"));
        assertEquals(false,userController.existsNickname("nickname1").getBody());
        assertEquals(true,userController.existsNickname("nick").getBody());
    }
}