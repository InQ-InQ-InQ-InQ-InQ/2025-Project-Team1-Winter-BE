package club.inq.team1.service;

import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.entity.User;
import java.util.Optional;

public interface UserService {
    Optional<User> acceptUser(UserJoinDTO userJoinDTO);
}
