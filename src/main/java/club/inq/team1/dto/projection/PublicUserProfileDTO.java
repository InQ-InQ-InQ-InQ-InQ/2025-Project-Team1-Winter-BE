package club.inq.team1.dto.projection;

import club.inq.team1.config.Gender;
import lombok.Data;

@Data
public class PublicUserProfileDTO {
    private Long userId;
    private String email;
    private String nickname;
    private Gender gender;
}
