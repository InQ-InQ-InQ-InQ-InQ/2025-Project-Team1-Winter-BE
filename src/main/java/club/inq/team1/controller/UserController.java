package club.inq.team1.controller;

import club.inq.team1.dto.request.user.RequestUserInfoUpdateDTO;
import club.inq.team1.dto.request.user.RequestUserPasswordUpdateDTO;
import club.inq.team1.dto.request.user.RequestUserCreateDTO;
import club.inq.team1.dto.response.user.ResponseUserPrivateInfoDTO;
import club.inq.team1.dto.response.user.ResponseUserPublicInfoDTO;
import club.inq.team1.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "/api/users", description = "유저 관련")
public class UserController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원가입", responses = {
            @ApiResponse(responseCode = "200", description = "아이디 생성 성공"),
            @ApiResponse(responseCode = "499", description = "동일한 아이디를 가진 유저가 존재"),
            @ApiResponse(responseCode = "498", description = "동일한 닉네임을 가진 유저가 존재")
    })
    public ResponseEntity<ResponseUserPrivateInfoDTO> join(@RequestBody @Valid RequestUserCreateDTO requestUserCreateDTO) {
        if (userDetailsService.loadUserByUsername(requestUserCreateDTO.getUsername()) != null) {
            // 이미 같은 아이디를 가진 유저가 존재.
            return ResponseEntity.status(499).body(null);
        }
        if(userService.existsNicknameCheck(requestUserCreateDTO.getNickname())){
            // 같은 닉네임을 가진 유저가 존재.
            return ResponseEntity.status(498).body(null);
        }

        ResponseUserPrivateInfoDTO user = userService.createUser(requestUserCreateDTO);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/username")
    @Operation(summary = "중복된 아이디인지 확인", description = "중복된 아이디라면 true, 아니라면 false", responses = {
            @ApiResponse(responseCode = "200", description = "아이디 중복 조회 성공")
    })
    public ResponseEntity<Boolean> existsUsername(@RequestParam(value = "q") String username) {
        return ResponseEntity.ok(userDetailsService.loadUserByUsername(username) != null);
    }

    @GetMapping("/nickname")
    @Operation(summary = "중복된 닉네임인지 확인", description = "중복된 닉네임이라면 true, 아니라면 false", responses = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 조회 성공")
    })
    public ResponseEntity<Boolean> existsNickname(@RequestParam(value = "q") String nickname) {
        return ResponseEntity.ok(userService.existsNicknameCheck(nickname));
    }

    @GetMapping(value = "/my", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "현재 로그인 된 사용자 개인정보 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<ResponseUserPrivateInfoDTO> getCurrentUserPrivateInfo(){
        ResponseUserPrivateInfoDTO privateInfo = userService.getPrivateInfo();

        return ResponseEntity.ok(privateInfo);
    }

    @PutMapping(value = "/my/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "현재 로그인된 사용자의 개인정보 수정", responses = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "499", description = "닉네임 중복으로 인한 실패")
    })
    public ResponseEntity<ResponseUserPrivateInfoDTO> updateCurrentUserPrivateInfo(@RequestBody @Valid RequestUserInfoUpdateDTO requestUserInfoUpdateDTO){
        if(userService.existsNicknameCheck(requestUserInfoUpdateDTO.getNickname()) &&
                !userService.getPrivateInfo().getNickname().equals(requestUserInfoUpdateDTO.getNickname())){
            return ResponseEntity.status(499).body(null);
        }
        ResponseUserPrivateInfoDTO dto = userService.updatePrivateInfo(requestUserInfoUpdateDTO);
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/my/update",produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "비밀번호 변경", responses = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공")
    })
    public ResponseEntity<ResponseUserPrivateInfoDTO> updateCurrentUserPassword(@RequestBody @Valid RequestUserPasswordUpdateDTO requestUserPasswordUpdateDTO){
        ResponseUserPrivateInfoDTO dto = userService.updatePassword(requestUserPasswordUpdateDTO);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "다른 유저 프로필 조회 기능", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<ResponseUserPublicInfoDTO> getUserProfile(@PathVariable("id") Long id){
        ResponseUserPublicInfoDTO userProfile = userService.getUserProfile(id);

        return ResponseEntity.ok(userProfile);
    }

    @PostMapping(value = "/my/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "프로필 이미지 설정", responses = {
            @ApiResponse(responseCode = "200", description = "프로필 이미지 설정 완료")
    })
    public ResponseEntity<Boolean> setProfileImage(@RequestPart("image")MultipartFile image) {
        Boolean setImageSuccess = userService.setUserProfileImage(image);
        return ResponseEntity.ok(setImageSuccess);
    }

    @DeleteMapping(value = "/my/delete")
    @Operation(summary = "회원 탈퇴" ,responses = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 완료")
    })
    public ResponseEntity<Boolean> deleteMySelf(){
        Boolean deleteSuccess = userService.deleteMySelf();
        return ResponseEntity.ok(deleteSuccess);
    }
}
