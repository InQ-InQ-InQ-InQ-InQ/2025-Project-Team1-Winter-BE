package club.inq.team1.dto.request.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCommentCreateDTO {
    private Long postId; // 댓글이 달린 게시글의 고유 아이디
    private String content; // 댓글 내용
}
