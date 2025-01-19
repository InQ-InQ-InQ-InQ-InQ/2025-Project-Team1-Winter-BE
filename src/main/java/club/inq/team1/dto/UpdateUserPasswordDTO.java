package club.inq.team1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(title = "패스워드 변경")
public class UpdateUserPasswordDTO {
    @NotNull
    @NotBlank
    @Schema(description = "변경하려는 패스워드")
    private String password;
}
