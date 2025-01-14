package club.inq.team1.service.impl;

import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.entity.User;
import club.inq.team1.repository.UserRepository;
import club.inq.team1.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User acceptUser(UserJoinDTO userJoinDTO) {
        User user = new User();

        user.setUsername(userJoinDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userJoinDTO.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getCurrentLoginUser() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(details instanceof User user){
            log.debug("Current Login User : " + user.getUsername());
            log.debug("Current Login User's Authority : " + user.getAuthorities().stream().findFirst().orElse(null));
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
