package club.inq.team1.dto.request.post.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestReplyUpdateDTO {
    private Long replyId;
    private String content;
}
