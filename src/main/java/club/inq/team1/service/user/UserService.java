package club.inq.team1.service.user;

import club.inq.team1.dto.request.PutUserPrivateInfoDTO;
import club.inq.team1.dto.request.UpdateUserPasswordDTO;
import club.inq.team1.dto.request.UserJoinDTO;
import club.inq.team1.dto.response.user.ResponseUserPrivateInfoDTO;
import club.inq.team1.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User acceptUser(UserJoinDTO userJoinDTO);
    boolean existsNicknameCheck(String nickname);
    User getPrivateInfo();
    User updatePrivateInfo(PutUserPrivateInfoDTO putUserPrivateInfoDTO);
    User updatePassword(UpdateUserPasswordDTO updateUserPasswordDTO);
    User getUserProfile(Long id);
    boolean setUserProfileImage(MultipartFile multipartFile);
    ResponseUserPrivateInfoDTO toResponseUserPrivateInfoDTO(User user);
    // 해당 유저가 존재하는지 확인한다.
    User getUserOrThrow(Long userId);
    boolean deleteMySelf();
}
