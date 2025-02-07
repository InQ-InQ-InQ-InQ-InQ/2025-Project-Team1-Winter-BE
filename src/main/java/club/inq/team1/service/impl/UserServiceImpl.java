package club.inq.team1.service.impl;

import club.inq.team1.constant.ImagePath;
import club.inq.team1.dto.projection.ProfileImageProjectionDTO;
import club.inq.team1.dto.request.PutUserPrivateInfoDTO;
import club.inq.team1.dto.request.UpdateUserPasswordDTO;
import club.inq.team1.dto.request.UserJoinDTO;
import club.inq.team1.entity.User;
import club.inq.team1.entity.UserInfo;
import club.inq.team1.repository.UserInfoRepository;
import club.inq.team1.repository.UserRepository;
import club.inq.team1.service.UserService;
import club.inq.team1.util.CurrentUser;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
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
    public User acceptUser(UserJoinDTO userJoinDTO) {
        User user = new User();

        user.setUsername(userJoinDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userJoinDTO.getPassword()));
        User saved = userRepository.save(user);
        // todo mapper 로 변경 필요
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(saved);
        userInfo.setFirstName(userJoinDTO.getFirstName());
        userInfo.setLastName(userJoinDTO.getLastName());
        userInfo.setNickname(userJoinDTO.getNickname());
        userInfo.setPhone(userJoinDTO.getPhone());
        userInfo.setEmail(userJoinDTO.getEmail());
        userInfo.setBirth(userJoinDTO.getBirth());
        userInfo.setGender(userJoinDTO.getGender());
        userInfoRepository.save(userInfo);

        return user;
    }

    @Override
    public boolean existsNicknameCheck(String nickname) {
        return userInfoRepository.existsByNickname(nickname);
    }

    @Override
    public User getPrivateInfo() {
        User user = currentUser.get();
        return userRepository.findById(user.getUserId()).orElseThrow();
    }

    @Override
    @Transactional
    public User updatePrivateInfo(PutUserPrivateInfoDTO putUserPrivateInfoDTO) {
        User user = currentUser.get();

        UserInfo userInfoId = user.getUserInfo();
        userInfoId.setNickname(putUserPrivateInfoDTO.getNickname());
        userInfoId.setPhone(putUserPrivateInfoDTO.getPhone());
        userInfoId.setEmail(putUserPrivateInfoDTO.getEmail());

        userInfoRepository.save(userInfoId);

        return user;
    }

    @Override
    @Transactional
    public User updatePassword(UpdateUserPasswordDTO updateUserPasswordDTO) {
        User user = currentUser.get();
        user.setPassword(passwordEncoder.encode(updateUserPasswordDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserProfile(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public boolean setUserProfileImage(MultipartFile multipartFile) {
        UserInfo userInfo = currentUser.get().getUserInfo();

        // 이전 이미지 제거
        deletePrevProfileImageFile(userInfo);

        // 프로필 이미지 저장 경로 C:/images/profile/yyyyMMdd/randomUUID+originalName.format
        String profilePath = ImagePath.SAVE_PROFILE.getPath();

        String imageFileStoredName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        String filePath = profilePath + imageFileStoredName;
        try {
            File f = new File(filePath);
            f.mkdirs();
            multipartFile.transferTo(f);
            userInfo.setProfileImagePath(filePath);
            userInfoRepository.save(userInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
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
    public byte[] getUserProfileImage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        ProfileImageProjectionDTO profileImageProjectionDTO = userInfoRepository.findProfileImagePathByUser(user)
                .orElseThrow();

        String profileImagePath = profileImageProjectionDTO.getProfileImagePath();

        try {
            return Files.readAllBytes(Path.of(profileImagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
