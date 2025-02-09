package club.inq.team1.service.post;

import club.inq.team1.dto.PostRequestDto;
import club.inq.team1.dto.response.post.ResponseCommentDTO;
import club.inq.team1.entity.Comment;

/**
 * 나중에 CommentService 로 바꿀 예정
 */
public interface CommentService {
    Boolean deleteComment(Long commentId);
    Boolean updateComment(Long commentId);
    Boolean createComment(PostRequestDto postRequestDto);

    ResponseCommentDTO toResponseCommentDTO(Comment comment);
}
