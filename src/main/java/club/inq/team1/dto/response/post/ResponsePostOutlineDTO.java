package club.inq.team1.dto.response.post;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponsePostOutlineDTO {
    private String title; // 게시글 제목
    private Long userId; // 게시글 작성자 고유 아이디
    private Long postId; // 게시글 고유 아이디
    private String nickname; // 게시글 작성자 닉네임
    private String region; // 게시글 지역 태그
    private BigDecimal latitude; // 게시글의 위도
    private BigDecimal longitude; // 게시글의 경도
    private Boolean myLike; // 현재 로그인한 유저의 게시글 좋아요 여부
    private String tags; // 태그
    private LocalDateTime createdAt; // 게시글 작성 시기
    private LocalDateTime modifiedAt; // 게시글 수정 시기
    private Integer postLikeCount; // 게시글 좋아요 개수
    private String imagePath; // 게시글 대표 이미지 경로
}
