package club.inq.team1.dto.response;

import club.inq.team1.config.Gender;
import java.util.Date;
import lombok.Data;

@Data
public class ResponseFollowDTO {
    Long userId;
    String username;
    String nickname;
    String phone;
    Gender gender;
    Date birth;
}
