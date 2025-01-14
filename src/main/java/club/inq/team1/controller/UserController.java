package club.inq.team1.controller;

import club.inq.team1.dto.UserJoinDTO;
import club.inq.team1.entity.User;
import club.inq.team1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Autowired
    public UserController(UserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/join")
    @Operation(summary = "회원가입",
            responses = {
                    @ApiResponse(responseCode = "201", description = "아이디 생성 성공"),
                    @ApiResponse(responseCode = "401", description = "동일한 아이디를 가진 유저가 존재")
            }
    )
    public ResponseEntity<User> join(@RequestBody @Valid UserJoinDTO userJoinDTO) {
        if (userDetailsService.loadUserByUsername(userJoinDTO.getUsername()) != null) {
            // 이미 같은 아이디를 가진 유저가 존재.
            return ResponseEntity.status(401).body(null);
        }

        User user = userService.acceptUser(userJoinDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
