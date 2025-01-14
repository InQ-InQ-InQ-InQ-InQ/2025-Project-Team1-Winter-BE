package club.inq.team1.service;

import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.entity.User;

public interface UserService {
    User acceptUser(UserJoinDTO userJoinDTO);
}
