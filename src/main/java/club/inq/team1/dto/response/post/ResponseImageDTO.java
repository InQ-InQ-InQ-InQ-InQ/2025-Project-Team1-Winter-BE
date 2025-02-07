package club.inq.team1.dto.response.post;

import lombok.Setter;

@Setter
public class ResponseImageDTO {
    private Long imageId; // 이미지 고유 아이디
    private Long postId; // 이미지가 게시된 게시글의 고유 아이디
    private String imagePath; // 이미지 저장 경로
}
