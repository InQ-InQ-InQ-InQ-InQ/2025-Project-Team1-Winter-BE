package club.inq.team1.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.entity.User;
import club.inq.team1.entity.UserInfo;
import club.inq.team1.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    @Transactional
    @DisplayName("User Entity에서 UserInfo 참조 테스트")
    void acceptUser() {
        UserJoinDTO userJoinDTO = new UserJoinDTO("qwer","1234","nick");
        User user = userService.acceptUser(userJoinDTO);

        assertEquals(user.getUserInfoId().getNickname(),"nick");
    }

    @Test
    @Transactional
    @DisplayName("UserInfo에서 User Entity 참조 테스트")
    void acceptUser1(){
        UserJoinDTO userJoinDTO = new UserJoinDTO("qwer","1234","nick");
        User user = userService.acceptUser(userJoinDTO);
        UserInfo userInfoId = user.getUserInfoId();

        String username = userInfoId.getUserId().getUsername();
        assertEquals(username,"qwer");
    }
}