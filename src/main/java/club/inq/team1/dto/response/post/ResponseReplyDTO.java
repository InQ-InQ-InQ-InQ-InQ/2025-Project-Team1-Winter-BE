package club.inq.team1.dto.response.post;

import java.time.LocalDateTime;
import lombok.Setter;

/**
 * @apiNote {@link club.inq.team1.entity.Reply}
 *
 * 답글에 대한 데이터를 응답용으로 가공한 DTO 입니다.
 */
@Setter
public class ResponseReplyDTO {
    private Long replyId; // 답글 고유 아이디
    private Long userId; // 답글을 단 유저의 고유 아이디
    private Long commentId; // 댓글의 고유 아이디
    private Boolean myReply; // 답글 작성자가 현재 로그인한 사용자인지 여부
    private String nickname; // 답글을 단 유저의 닉네임
    private String content; // 답글 내용
    private Boolean myLike; // 현재 로그인한 유저가 좋아요를 눌렀는지 여부
    private Long replyLikeCount; // 답글의 좋아요 개수
    private LocalDateTime createdAt; // 답글 생성 시기
    private LocalDateTime modifiedAt; // 답글 수정 시기
}
