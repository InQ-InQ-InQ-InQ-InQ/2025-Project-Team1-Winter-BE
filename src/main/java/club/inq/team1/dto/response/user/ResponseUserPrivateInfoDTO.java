package club.inq.team1.dto.response.user;

import club.inq.team1.constant.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @apiNote {@link club.inq.team1.entity.User}
 */

@Setter
@Getter
public class ResponseUserPrivateInfoDTO {
    // User
    private Long userId;
    private String username;

    // UserInfo
    private String nickname;
    private Gender gender;
    private LocalDate birth;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String profileImagePath;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
