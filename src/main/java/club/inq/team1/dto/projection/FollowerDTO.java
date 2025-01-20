package club.inq.team1.dto.projection;

import club.inq.team1.config.Gender;
import java.util.Date;

public interface FollowerDTO {
    Follower getFollower();
    interface Follower {
        Long getUserId();
        String getUsername();
        FollowerInfo getUserInfo();
        interface FollowerInfo {
            String getNickname();
            String getPhone();
            Gender getGender();
            Date getBirth();
        }
    }
}
