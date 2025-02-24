package club.inq.team1.repository.user;

import club.inq.team1.entity.Mail;
import club.inq.team1.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail,Long> {
    /**
     * 수신자의 아이디로 온 메일을 찾는다.
     * @param user 수신자
     * @param pageable 페이징
     * @return 수신자의 아이디로 온 메일들.
     */
    Slice<Mail> findByUser(User user, Pageable pageable);
}
