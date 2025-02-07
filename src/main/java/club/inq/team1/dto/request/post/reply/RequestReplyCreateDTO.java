package club.inq.team1.dto.request.post.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestReplyCreateDTO {
    private Long commentId; // 답글이 달린 댓글의 고유 아이디
    private String content; // 답글 내용
}
