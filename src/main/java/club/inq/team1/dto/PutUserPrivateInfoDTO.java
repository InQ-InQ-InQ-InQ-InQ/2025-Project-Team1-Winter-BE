package club.inq.team1.dto;

import club.inq.team1.config.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(title = "개인 정보 수정")
public class PutUserPrivateInfoDTO {
    @NotNull
    @NotBlank
    @NonNull
    @Schema(name = "변경할 닉네임", description = "반드시 중복 검사를 진행한 후에 진행해야함.")
    private String nickname;
    
    @Schema(name = "변경할 이메일")
    private String email;
    
    @Schema(name = "변경할 휴대폰 번호")
    private String phone;

    @Schema(name = "변경할 성별")
    private Gender gender;
    
    @Schema(name = "변경할 생일")
    private Date birth;
}
