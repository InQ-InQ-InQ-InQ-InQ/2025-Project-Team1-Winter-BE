package club.inq.team1.dto.response.post;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Setter;

@Setter
public class ResponsePostDTO {
    private Long postId; // 게시글 고유 아이디
    private Long userId; // 게시글 작성자의 고유 아이디
    private String nickname; // 게시글 작성자의 닉네임
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String tags; // 게시글 태그
    private String where; // 게시글 지역 태그
    private BigDecimal latitude; // 게시글의 위도
    private BigDecimal longitude; // 게시글의 경도
    private Long likeCount; // 게시글의 좋아요 개수
    private Boolean myLike; // 현재 로그인한 유저의 게시글 좋아요 여부
    private LocalDateTime createdAt; // 게시글 작성 시기
    private LocalDateTime modifiedAt; // 게시글 수정 시기
    private List<ResponseImageDTO> images; // 게시글 이미지 데이터
    private List<ResponseCommentDTO> comments; // 게시글 댓글 데이터
}
