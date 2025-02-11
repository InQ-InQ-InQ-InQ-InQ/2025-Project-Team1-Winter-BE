package club.inq.team1.service.post;

import club.inq.team1.dto.request.post.comment.RequestCommentCreateDTO;
import club.inq.team1.dto.request.post.comment.RequestCommentUpdateDTO;
import club.inq.team1.dto.response.post.ResponseCommentDTO;
import club.inq.team1.entity.Comment;

public interface CommentService {
    Boolean deleteComment(Long commentId);
    Boolean updateComment(Long commentId, RequestCommentUpdateDTO requestCommentUpdateDTO);
    Boolean createComment(RequestCommentCreateDTO requestCommentCreateDTO);

    ResponseCommentDTO toResponseCommentDTO(Comment comment);
}
