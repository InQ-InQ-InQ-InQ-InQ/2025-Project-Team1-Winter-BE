package club.inq.team1.util.mapper;

import club.inq.team1.dto.projection.FollowerDTO;
import club.inq.team1.dto.projection.FollowingDTO;
import club.inq.team1.dto.response.ResponseFollowDTO;

public class FollowMapper {
    public static ResponseFollowDTO toResponseFollowDTO(FollowerDTO followerDTO){
        ResponseFollowDTO responseFollowDTO = new ResponseFollowDTO();
        responseFollowDTO.setUserId(followerDTO.getFollowerId().getUserId());
        responseFollowDTO.setUsername(followerDTO.getFollowerId().getUsername());
        responseFollowDTO.setNickname(followerDTO.getFollowerId().getUserInfoId().getNickname());
        responseFollowDTO.setPhone(followerDTO.getFollowerId().getUserInfoId().getPhone());
        responseFollowDTO.setGender(followerDTO.getFollowerId().getUserInfoId().getGender());
        responseFollowDTO.setBirth(followerDTO.getFollowerId().getUserInfoId().getBirth());
        return responseFollowDTO;
    }

    public static ResponseFollowDTO toResponseFollowDTO(FollowingDTO followerDTO){
        ResponseFollowDTO responseFollowDTO = new ResponseFollowDTO();
        responseFollowDTO.setUserId(followerDTO.getFolloweeId().getUserId());
        responseFollowDTO.setUsername(followerDTO.getFolloweeId().getUsername());
        responseFollowDTO.setNickname(followerDTO.getFolloweeId().getUserInfoId().getNickname());
        responseFollowDTO.setPhone(followerDTO.getFolloweeId().getUserInfoId().getPhone());
        responseFollowDTO.setGender(followerDTO.getFolloweeId().getUserInfoId().getGender());
        responseFollowDTO.setBirth(followerDTO.getFolloweeId().getUserInfoId().getBirth());
        return responseFollowDTO;
    }
}
