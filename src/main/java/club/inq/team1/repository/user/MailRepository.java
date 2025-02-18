package club.inq.team1.repository.user;

import club.inq.team1.entity.Mail;
import club.inq.team1.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail,Long> {
    /**
     * 수신자의 아이디로 온 메일을 찾는다.
     * @param user 수신자
     * @return 수신자의 아이디로 온 메일들.
     */
    List<Mail> findByUser(User user);
}
