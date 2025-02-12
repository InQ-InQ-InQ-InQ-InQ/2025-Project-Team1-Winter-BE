package club.inq.team1.util.mapper;

import club.inq.team1.dto.projection.FollowerDTO;
import club.inq.team1.dto.projection.FollowingDTO;
import club.inq.team1.dto.response.ResponseFollowDTO;

public class FollowMapper {
    public static ResponseFollowDTO toResponseFollowDTO(FollowerDTO followerDTO){
        ResponseFollowDTO responseFollowDTO = new ResponseFollowDTO();
        responseFollowDTO.setUserId(followerDTO.getFollower().getUserId());
        responseFollowDTO.setUsername(followerDTO.getFollower().getUsername());
        responseFollowDTO.setNickname(followerDTO.getFollower().getUserInfo().getNickname());
        responseFollowDTO.setPhone(followerDTO.getFollower().getUserInfo().getPhone());
        responseFollowDTO.setGender(followerDTO.getFollower().getUserInfo().getGender());
        responseFollowDTO.setBirth(followerDTO.getFollower().getUserInfo().getBirth());
        return responseFollowDTO;
    }

    public static ResponseFollowDTO toResponseFollowDTO(FollowingDTO followerDTO){
        ResponseFollowDTO responseFollowDTO = new ResponseFollowDTO();
        responseFollowDTO.setUserId(followerDTO.getFollowee().getUserId());
        responseFollowDTO.setUsername(followerDTO.getFollowee().getUsername());
        responseFollowDTO.setNickname(followerDTO.getFollowee().getUserInfo().getNickname());
        responseFollowDTO.setPhone(followerDTO.getFollowee().getUserInfo().getPhone());
        responseFollowDTO.setGender(followerDTO.getFollowee().getUserInfo().getGender());
        responseFollowDTO.setBirth(followerDTO.getFollowee().getUserInfo().getBirth());
        return responseFollowDTO;
    }
}
