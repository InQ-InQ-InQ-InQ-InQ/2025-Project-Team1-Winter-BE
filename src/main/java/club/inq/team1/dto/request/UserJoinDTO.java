package club.inq.team1.dto.request;

import club.inq.team1.config.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "회원가입 요청 DTO")
public class UserJoinDTO {
    @NotNull
    @NotBlank
    @NonNull
    @Schema(description = "로그인 아이디", example = "hello1234")
    private String username;

    @NotNull
    @NotBlank
    @NonNull
    @Schema(description = "로그인 패스워드", example = "qwer1234")
    private String password;

    @NotNull
    @NotBlank
    @NonNull
    @Schema(description = "닉네임",example = "hello")
    private String nickname;

    @NotNull
    @NotBlank
    @NonNull
    @Schema(description = "성", example = "Kim")
    private String firstName;

    @NotNull
    @NotBlank
    @NonNull
    @Schema(description = "이름", example = "Sumin")
    private String lastName;

    @Schema(description = "전화번호",example = "010-1234-5678")
    private String phone;

    @Schema(description = "이메일",example = "hello1234@naver.com")
    private String email;

    @Schema(description = "생년월일",example = "2001-01-01")
    private LocalDate birth;

    @Schema(description = "성별",example = "FEMALE")
    private Gender gender;
}
