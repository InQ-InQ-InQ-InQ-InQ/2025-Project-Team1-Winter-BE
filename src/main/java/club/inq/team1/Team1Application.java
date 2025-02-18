package club.inq.team1;

import club.inq.team1.entity.User;
import club.inq.team1.entity.UserInfo;
import club.inq.team1.repository.user.UserInfoRepository;
import club.inq.team1.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class Team1Application implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(Team1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(encoder.encode("1234"));
        user = userRepository.save(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo.setNickname("HELLO");
        userInfo.setFirstName("Cho");
        userInfo.setLastName("Gunhee");
        userInfoRepository.save(userInfo);
    }
}
