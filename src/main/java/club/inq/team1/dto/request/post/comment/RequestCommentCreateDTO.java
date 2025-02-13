package club.inq.team1.dto.request.post.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCommentCreateDTO {
    private Long postId;
    private String content;
}
