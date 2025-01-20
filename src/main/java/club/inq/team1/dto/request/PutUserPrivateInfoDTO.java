package club.inq.team1.dto.request;

import club.inq.team1.config.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "개인 정보 수정")
public class PutUserPrivateInfoDTO {
    @NotNull
    @NotBlank
    @NonNull
    @Schema(description = "반드시 중복 검사를 진행한 후에 진행해야함.")
    private String nickname;
    
    @Schema(description = "변경할 이메일")
    private String email;
    
    @Schema(description = "변경할 휴대폰 번호")
    private String phone;

    @Schema(description = "변경할 성별")
    private Gender gender;
    
    @Schema(description = "변경할 생일")
    private Date birth;
}
