package club.inq.team1.dto.response.post;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Setter;

/**
 * @apiNote {@link club.inq.team1.entity.Comment}
 *
 * 댓글에 대한 데이터를 응답용으로 가공한 DTO 입니다.
 */
@Setter
public class ResponseCommentDTO {
    private Long commentId; // 댓글 고유 아이디
    private Long userId; // 댓글을 작성한 유저의 고유 아이디
    private Long postId; // 게시글의 고유 아이디
    private String nickname; // 댓글을 작성한 유저의 닉네임
    private String content; // 댓글 내용
    private Boolean myLike; // 현재 로그인한 사용자의 좋아요 여부
    private Long commentLikeCount; // 댓글의 좋아요 개수
    private LocalDateTime createdAt; // 댓글의 작성 시기
    private LocalDateTime modifiedAt; // 댓글의 수정 시기
    private List<ResponseReplyDTO> replies; // 답글
}
