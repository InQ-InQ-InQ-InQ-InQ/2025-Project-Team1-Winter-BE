package club.inq.team1.service.user;

import club.inq.team1.dto.request.user.RequestUserInfoUpdateDTO;
import club.inq.team1.dto.request.user.RequestUserPasswordUpdateDTO;
import club.inq.team1.dto.request.user.RequestUserCreateDTO;
import club.inq.team1.dto.response.user.ResponseUserPrivateInfoDTO;
import club.inq.team1.dto.response.user.ResponseUserPublicInfoDTO;
import club.inq.team1.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseUserPrivateInfoDTO createUser(RequestUserCreateDTO requestUserCreateDTO);
    boolean existsNicknameCheck(String nickname);
    ResponseUserPrivateInfoDTO getPrivateInfo();
    ResponseUserPrivateInfoDTO updatePrivateInfo(RequestUserInfoUpdateDTO requestUserInfoUpdateDTO);
    ResponseUserPrivateInfoDTO updatePassword(RequestUserPasswordUpdateDTO requestUserPasswordUpdateDTO);
    ResponseUserPublicInfoDTO getUserProfile(Long id);
    boolean setUserProfileImage(MultipartFile multipartFile);
    ResponseUserPrivateInfoDTO toResponseUserPrivateInfoDTO(User user);

    ResponseUserPublicInfoDTO toResponseUserPublicInfoDTO(User user);

    // 해당 유저가 존재하는지 확인한다.
    User getUserOrThrow(Long userId);
    boolean deleteMySelf();
}
