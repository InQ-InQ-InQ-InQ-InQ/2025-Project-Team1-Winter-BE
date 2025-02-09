package club.inq.team1.dto.response.post;

import lombok.Setter;

/**
 * @apiNote {@link club.inq.team1.entity.Image}
 *
 * 이미지 저장 경로에 대한 엔티티 응답용 DTO 입니다.
 */
@Setter
public class ResponseImageDTO {
    private Long imageId; // 이미지 고유 아이디
    private Long postId; // 이미지가 게시된 게시글의 고유 아이디
    private String imagePath; // 이미지 저장 경로
    private String originalName; // 이미지 원래 이름.
}
