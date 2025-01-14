package club.inq.team1.dto;

import club.inq.team1.config.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinDTO {
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String nickname;

    private String phone;
    private String email;
    private Date birth;
    private Gender gender;
}
