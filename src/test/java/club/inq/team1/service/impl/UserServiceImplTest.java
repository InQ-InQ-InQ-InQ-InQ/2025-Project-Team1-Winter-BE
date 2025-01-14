package club.inq.team1.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.entity.User;
import club.inq.team1.entity.UserInfo;
import club.inq.team1.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    @Transactional
    void acceptUser() {
        UserJoinDTO userJoinDTO = new UserJoinDTO("qwer","1234","nick",null,null);
        User user = userService.acceptUser(userJoinDTO);

        assertEquals(user.getUserInfoId().getNickname(),"nick" );
    }
}