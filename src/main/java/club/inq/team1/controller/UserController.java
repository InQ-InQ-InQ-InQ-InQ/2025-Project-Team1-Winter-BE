package club.inq.team1.controller;

import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.entity.User;
import club.inq.team1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
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
            @ApiResponse(responseCode = "201", description = "아이디 생성 성공"),
            @ApiResponse(responseCode = "401", description = "동일한 아이디를 가진 유저가 존재"),
            @ApiResponse(responseCode = "500", description = "동일한 닉네임을 가진 유저가 존재")
    })
    public ResponseEntity<User> join(@RequestBody @Valid UserJoinDTO userJoinDTO) {
        if (userDetailsService.loadUserByUsername(userJoinDTO.getUsername()) != null) {
            // 이미 같은 아이디를 가진 유저가 존재.
            return ResponseEntity.status(401).body(null);
        }

        User user = userService.acceptUser(userJoinDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
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
}
