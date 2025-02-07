package club.inq.team1.service;

import club.inq.team1.dto.PostRequestDto;

/**
 * 나중에 CommentService 로 바꿀 예정
 */
public interface ComService {
    Boolean deleteComment(Long commentId);
    Boolean updateComment(Long commentId);
    Boolean createComment(PostRequestDto postRequestDto);
}
