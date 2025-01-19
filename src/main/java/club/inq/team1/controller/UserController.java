package club.inq.team1.controller;

import club.inq.team1.dto.PutUserPrivateInfoDTO;
import club.inq.team1.dto.UpdateUserPasswordDTO;
import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.dto.projection.PublicUserProfileDTO;
import club.inq.team1.entity.User;
import club.inq.team1.entity.UserInfo;
import club.inq.team1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "user와 관련된 api 컨트롤러")
public class UserController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Autowired
    public UserController(UserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/join")
    @Operation(summary = "회원가입", responses = {
            @ApiResponse(responseCode = "200", description = "아이디 생성 성공"),
            @ApiResponse(responseCode = "299", description = "동일한 아이디를 가진 유저가 존재"),
            @ApiResponse(responseCode = "298", description = "동일한 닉네임을 가진 유저가 존재")
    })
    public ResponseEntity<User> join(@RequestBody @Valid UserJoinDTO userJoinDTO) {
        if (userDetailsService.loadUserByUsername(userJoinDTO.getUsername()) != null) {
            // 이미 같은 아이디를 가진 유저가 존재.
            return ResponseEntity.status(299).body(null);
        }
        if(userService.existsNicknameCheck(userJoinDTO.getNickname())){
            // 같은 닉네임을 가진 유저가 존재.
            return ResponseEntity.status(298).body(null);
        }

        User user = userService.acceptUser(userJoinDTO);

        return ResponseEntity.status(200).body(user);
    }

    @GetMapping("/username")
    @Operation(summary = "중복된 아이디인지 확인", description = "중복된 아이디라면 true, 아니라면 false", responses = {
            @ApiResponse(responseCode = "200", description = "아이디 중복 조회 성공")
    })
    public ResponseEntity<Boolean> existsUsername(@RequestParam(value = "q") String username) {
        return ResponseEntity.status(200).body(userDetailsService.loadUserByUsername(username) != null);
    }

    @GetMapping("/nickname")
    @Operation(summary = "중복된 닉네임인지 확인", description = "중복된 닉네임이라면 true, 아니라면 false", responses = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 조회 성공")
    })
    public ResponseEntity<Boolean> existsNickname(@RequestParam(value = "q") String nickname) {
        return ResponseEntity.status(200).body(userService.existsNicknameCheck(nickname));
    }

    @GetMapping(value = "/my", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "현재 로그인 된 사용자 개인정보 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<User> getCurrentUserPrivateInfo(){
        User user = userService.getPrivateInfo();
        return ResponseEntity.status(200).body(user);
    }

    @PutMapping(value = "/my/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "현재 로그인된 사용자의 개인정보 수정", responses = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "299", description = "닉네임 중복으로 인한 실패")
    })
    public ResponseEntity<User> updateCurrentUserPrivateInfo(@RequestBody @Valid PutUserPrivateInfoDTO putUserPrivateInfoDTO){
        if(userService.existsNicknameCheck(putUserPrivateInfoDTO.getNickname()) &&
                !userService.getPrivateInfo().getUserInfoId().getNickname().equals(putUserPrivateInfoDTO.getNickname())){
            return ResponseEntity.status(299).body(null);
        }
        User user = userService.updatePrivateInfo(putUserPrivateInfoDTO);
        return ResponseEntity.status(200).body(user);
    }

    @PostMapping(value = "/my/update",produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "비밀번호 변경", responses = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공")
    })
    public ResponseEntity<User> updateCurrentUserPassword(@RequestBody @Valid UpdateUserPasswordDTO updateUserPasswordDTO){
        User user = userService.updatePassword(updateUserPasswordDTO);
        return ResponseEntity.status(200).body(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "다른 유저 프로필 조회 기능", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<PublicUserProfileDTO> getUserProfile(@PathVariable("id") Long id){
        User user = userService.getUserProfile(id);
        UserInfo userInfoId = user.getUserInfoId();

        PublicUserProfileDTO publicUserProfileDTO = new PublicUserProfileDTO();
        publicUserProfileDTO.setUserId(user.getUserId());
        publicUserProfileDTO.setNickname(userInfoId.getNickname());
        publicUserProfileDTO.setEmail(userInfoId.getEmail());
        publicUserProfileDTO.setGender(userInfoId.getGender());

        return ResponseEntity.status(200).body(publicUserProfileDTO);
    }
}
