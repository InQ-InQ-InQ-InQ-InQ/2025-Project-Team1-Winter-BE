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
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User acceptUser(UserJoinDTO userJoinDTO) {
        User user = new User();

        user.setUsername(userJoinDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userJoinDTO.getPassword()));
        User saved = userRepository.save(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setUser(saved);
        userInfo.setNickname(userJoinDTO.getNickname());
        userInfo.setPhone(userJoinDTO.getPhone());
        userInfo.setEmail(userJoinDTO.getEmail());
        userInfo.setBirth(userJoinDTO.getBirth());
        userInfo.setGender(userJoinDTO.getGender());
        userInfoRepository.save(userInfo);

        return user;
    }

    @Override
    public Optional<User> getCurrentLoginUser() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(details instanceof User user){
            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public boolean existsNicknameCheck(String nickname) {
        return userInfoRepository.existsByNickname(nickname);
    }

    @Override
    public User getPrivateInfo() {
        User user = getCurrentLoginUser().orElseThrow();
        return userRepository.findById(user.getUserId()).orElseThrow();
    }

    @Override
    @Transactional
    public User updatePrivateInfo(PutUserPrivateInfoDTO putUserPrivateInfoDTO){
        User user = getCurrentLoginUser().orElseThrow();

        UserInfo userInfoId = user.getUserInfo();
        userInfoId.setNickname(putUserPrivateInfoDTO.getNickname());
        userInfoId.setPhone(putUserPrivateInfoDTO.getPhone());
        userInfoId.setEmail(putUserPrivateInfoDTO.getEmail());
        userInfoId.setGender(putUserPrivateInfoDTO.getGender());
        userInfoId.setBirth(putUserPrivateInfoDTO.getBirth());

        userInfoRepository.save(userInfoId);

        return user;
    }

    @Override
    @Transactional
    public User updatePassword(UpdateUserPasswordDTO updateUserPasswordDTO) {
        User user = getCurrentLoginUser().orElseThrow();
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
        UserInfo userInfo = getCurrentLoginUser().orElseThrow().getUserInfo();
        
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
        if(profileImagePath != null) {
            File prevProfileImage = Path.of(profileImagePath).toFile();
            if (prevProfileImage.exists()) {
                prevProfileImage.delete();
            }
        }
    }

    @Override
    public byte[] getUserProfileImage(Long userId){
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
    public boolean deleteMySelf(){
        Optional<User> opUser = getCurrentLoginUser();
        if(opUser.isEmpty())
            return false;

        User user = opUser.get();
        UserInfo userInfo = user.getUserInfo();
        deletePrevProfileImageFile(userInfo);
        userInfoRepository.delete(userInfo);
        userRepository.delete(user);
        return true;
    }
}
