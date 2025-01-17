package club.inq.team1.dto.projection;

import club.inq.team1.config.Gender;
import lombok.Data;

@Data
public class PublicUserProfileDTO {
    private Long userId;
    private PublicUserInfo publicUserInfo;
    @Data
    public static class PublicUserInfo {
        private String email;
        private String nickname;
        private Gender gender;
    }
}
