package club.inq.team1.service.impl.user;

import club.inq.team1.constant.ImagePath;
import club.inq.team1.dto.request.user.RequestUserInfoUpdateDTO;
import club.inq.team1.dto.request.user.RequestUserPasswordUpdateDTO;
import club.inq.team1.dto.request.user.RequestUserCreateDTO;
import club.inq.team1.dto.response.user.ResponseUserPrivateInfoDTO;
import club.inq.team1.dto.response.user.ResponseUserPublicInfoDTO;
import club.inq.team1.entity.User;
import club.inq.team1.entity.UserInfo;
import club.inq.team1.repository.user.UserInfoRepository;
import club.inq.team1.repository.user.UserRepository;
import club.inq.team1.service.user.UserService;
import club.inq.team1.util.CurrentUser;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final CurrentUser currentUser;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseUserPrivateInfoDTO createUser(RequestUserCreateDTO requestUserCreateDTO) {
        User user = new User();

        user.setUsername(requestUserCreateDTO.getUsername());
        user.setPassword(passwordEncoder.encode(requestUserCreateDTO.getPassword()));
        User saved = userRepository.save(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setUser(saved);
        userInfo.setFirstName(requestUserCreateDTO.getFirstName());
        userInfo.setLastName(requestUserCreateDTO.getLastName());
        userInfo.setNickname(requestUserCreateDTO.getNickname());
        userInfo.setPhone(requestUserCreateDTO.getPhone());
        userInfo.setEmail(requestUserCreateDTO.getEmail());
        userInfo.setBirth(requestUserCreateDTO.getBirth());
        userInfo.setGender(requestUserCreateDTO.getGender());
        userInfoRepository.save(userInfo);

        user.setUserInfo(userInfo);

        return toResponseUserPrivateInfoDTO(user);
    }

    @Override
    public boolean existsNicknameCheck(String nickname) {
        return userInfoRepository.existsByNickname(nickname);
    }

    @Override
    public ResponseUserPrivateInfoDTO getPrivateInfo() {
        User user = currentUser.get();
        return toResponseUserPrivateInfoDTO(user);
    }

    @Override
    @Transactional
    public ResponseUserPrivateInfoDTO updatePrivateInfo(RequestUserInfoUpdateDTO requestUserInfoUpdateDTO) {
        User user = currentUser.get();

        UserInfo userInfoId = user.getUserInfo();
        userInfoId.setNickname(requestUserInfoUpdateDTO.getNickname());
        userInfoId.setPhone(requestUserInfoUpdateDTO.getPhone());
        userInfoId.setEmail(requestUserInfoUpdateDTO.getEmail());

        userInfoRepository.save(userInfoId);

        user.setUserInfo(userInfoId);
        return toResponseUserPrivateInfoDTO(user);
    }

    @Override
    @Transactional
    public ResponseUserPrivateInfoDTO updatePassword(RequestUserPasswordUpdateDTO requestUserPasswordUpdateDTO) {
        User user = currentUser.get();
        user.setPassword(passwordEncoder.encode(requestUserPasswordUpdateDTO.getPassword()));
        User save = userRepository.save(user);
        return toResponseUserPrivateInfoDTO(save);
    }

    @Override
    public ResponseUserPublicInfoDTO getUserProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return toResponseUserPublicInfoDTO(user);
    }

    @Override
    @Transactional
    public boolean setUserProfileImage(MultipartFile multipartFile) {
        UserInfo userInfo = currentUser.get().getUserInfo();

        // 이전 이미지 제거
        deletePrevProfileImageFile(userInfo);

        // 프로필 이미지 저장 경로 C:/images/profile/yyyyMMdd/randomUUID+originalName.format
        String resourceStoredPath = ImagePath.WINDOW.getPath(); // 배포시 수정 필요
        String profilePath = ImagePath.SAVE_PROFILE.getPath();

        String imageFileStoredName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        String filePath = resourceStoredPath + profilePath + imageFileStoredName;
        try {
            File f = new File(filePath);
            f.mkdirs();
            multipartFile.transferTo(f);
            userInfo.setProfileImagePath(profilePath + imageFileStoredName);
            userInfoRepository.save(userInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public ResponseUserPrivateInfoDTO toResponseUserPrivateInfoDTO(User user) {
        ResponseUserPrivateInfoDTO dto = new ResponseUserPrivateInfoDTO();
        UserInfo userInfo = user.getUserInfo();

        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());

        dto.setNickname(userInfo.getNickname());
        dto.setGender(userInfo.getGender());
        dto.setBirth(userInfo.getBirth());
        dto.setPhone(userInfo.getPhone());
        dto.setFirstName(userInfo.getFirstName());
        dto.setLastName(userInfo.getLastName());
        dto.setEmail(userInfo.getEmail());
        dto.setCreatedAt(userInfo.getCreatedAt());
        return dto;
    }

    @Override
    public ResponseUserPublicInfoDTO toResponseUserPublicInfoDTO(User user){
        ResponseUserPublicInfoDTO dto = new ResponseUserPublicInfoDTO();
        UserInfo userInfo = user.getUserInfo();

        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());

        dto.setNickname(userInfo.getNickname());
        dto.setGender(userInfo.getGender());
        dto.setBirth(userInfo.getBirth());
        dto.setFirstName(userInfo.getFirstName());
        dto.setLastName(userInfo.getLastName());
        dto.setEmail(userInfo.getEmail());
        dto.setCreatedAt(userInfo.getCreatedAt());
        return dto;
    }

    @Override
    // 해당 유저가 존재하는지 확인한다.
    public User getUserOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));
    }

    private void deletePrevProfileImageFile(UserInfo userInfo) {
        String profileImagePath = userInfo.getProfileImagePath();
        if (profileImagePath != null) {
            File prevProfileImage = Path.of(profileImagePath).toFile();
            if (prevProfileImage.exists()) {
                prevProfileImage.delete();
            }
        }
    }

    @Override
    @Transactional
    public boolean deleteMySelf() {
        User user = currentUser.get();
        UserInfo userInfo = user.getUserInfo();
        deletePrevProfileImageFile(userInfo);
        userRepository.delete(user);
        return true;
    }
}
