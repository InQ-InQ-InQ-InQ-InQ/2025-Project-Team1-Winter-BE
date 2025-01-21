package club.inq.team1.service;

import club.inq.team1.dto.request.PutUserPrivateInfoDTO;
import club.inq.team1.dto.request.UpdateUserPasswordDTO;
import club.inq.team1.dto.request.UserJoinDTO;
import club.inq.team1.entity.User;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User acceptUser(UserJoinDTO userJoinDTO);
    Optional<User> getCurrentLoginUser();
    boolean existsNicknameCheck(String nickname);
    User getPrivateInfo();
    User updatePrivateInfo(PutUserPrivateInfoDTO putUserPrivateInfoDTO);
    User updatePassword(UpdateUserPasswordDTO updateUserPasswordDTO);
    User getUserProfile(Long id);
    boolean setUserProfileImage(MultipartFile multipartFile);
    byte[] getUserProfileImage(Long userId);

    boolean deleteMySelf();
}
