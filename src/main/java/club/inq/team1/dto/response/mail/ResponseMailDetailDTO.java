package club.inq.team1.dto.response.mail;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @apiNote {@link club.inq.team1.entity.Mail}
 */
@Getter
@Setter
public class ResponseMailDetailDTO {
    private Long postId; // 게시글 고유 아이디
    private Long userId; // 게시글 작성자의 고유 아이디
    private String nickname; // 게시글 작성자 닉네임
    private String title; // 게시글 제목
    private String imagePath; // 게시글 대표 이미지 경로
    private LocalDateTime createdAt; // 알람이 온 시간
    private Boolean saw; // 알람 확인 여부
}
