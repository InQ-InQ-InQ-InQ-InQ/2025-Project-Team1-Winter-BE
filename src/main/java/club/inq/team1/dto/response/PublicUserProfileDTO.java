package club.inq.team1.dto.response;

import club.inq.team1.config.Gender;
import lombok.Data;

@Data
public class PublicUserProfileDTO {
    private Long userId;
    private String nickname;
    private Gender gender;
}
