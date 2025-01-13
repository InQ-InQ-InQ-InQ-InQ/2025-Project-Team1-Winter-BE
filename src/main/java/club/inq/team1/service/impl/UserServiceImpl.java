package club.inq.team1.service.impl;

import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.entity.User;
import club.inq.team1.repository.UserRepository;
import club.inq.team1.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Optional<User> acceptUser(UserJoinDTO userJoinDTO) {
        User user = new User();

        user.setUsername(userJoinDTO.getUsername());
        user.setPassword(userJoinDTO.getPassword());

        User saved = userRepository.save(user);

        return Optional.of(saved);
    }
}
