package club.inq.team1.repository;

import club.inq.team1.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    @Query("select (count(u) > 0) from UserInfo u where u.nickname = ?1")
    boolean existsByNickname(String nickname);
}
