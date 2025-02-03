package club.inq.team1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    @NotBlank(message =  "댓글 내용은 비워둘 수 없습니다.")
    private String content;  // 댓글 내용

    public CommentRequestDto(String content) {
        this.content = content;
    }

}
