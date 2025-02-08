package club.inq.team1.dto.response.post;

import java.time.LocalDateTime;
import lombok.Setter;

@Setter
public class ResponsePostOutlineDTO {
    private String title; // 게시글 제목
    private Long userId; // 게시글 작성자 고유 아이디
    private Long postId; // 게시글 고유 아이디
    private String nickname; // 게시글 작성자 닉네임
    private String tags;
    private LocalDateTime createdAt; // 게시글 작성 시기
    private LocalDateTime modifiedAt; // 게시글 수정 시기
    private Integer postLikeCount; // 게시글 좋아요 개수
    private String imagePath; // 게시글 대표 이미지 경로
}
