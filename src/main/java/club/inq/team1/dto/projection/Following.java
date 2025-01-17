package club.inq.team1.dto.projection;

import club.inq.team1.config.Gender;
import java.util.Date;

public interface Following {
    Followee getFolloweeId();
    interface Followee {
        Long getUserId();
        String getUsername();
        FolloweeInfo getUserInfoId();
        interface FolloweeInfo {
            String getNickname();
            String getPhone();
            Gender getGender();
            Date getBirth();
        }
    }
}
