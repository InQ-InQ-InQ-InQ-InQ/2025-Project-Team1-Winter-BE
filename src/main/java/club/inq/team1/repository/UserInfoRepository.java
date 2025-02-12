package club.inq.team1.repository;

import club.inq.team1.dto.projection.ProfileImageProjectionDTO;
import club.inq.team1.entity.User;
import club.inq.team1.entity.UserInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    boolean existsByNickname(String nickname);
    Optional<ProfileImageProjectionDTO> findProfileImagePathByUser(User user);
}
