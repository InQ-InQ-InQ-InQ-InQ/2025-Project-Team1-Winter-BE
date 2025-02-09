package club.inq.team1.service.impl.post;

import club.inq.team1.dto.PostRequestDto;
import club.inq.team1.dto.response.post.ResponseCommentDTO;
import club.inq.team1.entity.Comment;
import club.inq.team1.entity.User;
import club.inq.team1.repository.CommentRepository;
import club.inq.team1.repository.post.CommentLikeRepository;
import club.inq.team1.service.post.CommentService;
import club.inq.team1.service.post.ReplyService;
import club.inq.team1.util.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CurrentUser currentUser;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReplyService replyService;

    @Override
    public Boolean deleteComment(Long commentId) {
        return null;
    }

    @Override
    public Boolean updateComment(Long commentId) {
        return null;
    }

    @Override
    public Boolean createComment(PostRequestDto postRequestDto) {
        return null;
    }

    @Override
    public ResponseCommentDTO toResponseCommentDTO(Comment comment) {
        ResponseCommentDTO dto = new ResponseCommentDTO();
        User user = currentUser.get();

        dto.setCommentId(comment.getId());
        dto.setUserId(comment.getUser().getUserId());
        dto.setPostId(comment.getPost().getPostId());
        dto.setMyComment(comment.getUser().getUserId().equals(user.getUserId()));
        dto.setNickname(comment.getUser().getUserInfo().getNickname());
        dto.setContent(comment.getContent());
        dto.setMyLike(commentLikeRepository.existsByUserAndComment(user,comment));
        dto.setCommentLikeCount(comment.getCommentLikes().size());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setModifiedAt(comment.getUpdatedAt());
        dto.setReplies(comment.getReplies().stream().map(replyService::toResponseReplyDTO).toList());

        return dto;
    }
}
