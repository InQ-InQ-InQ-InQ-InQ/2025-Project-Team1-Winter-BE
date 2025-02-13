package club.inq.team1.service.impl.post;

import club.inq.team1.dto.request.post.comment.RequestCommentCreateDTO;
import club.inq.team1.dto.request.post.comment.RequestCommentUpdateDTO;
import club.inq.team1.dto.response.post.ResponseCommentDTO;
import club.inq.team1.entity.Comment;
import club.inq.team1.entity.CommentLike;
import club.inq.team1.entity.Post;
import club.inq.team1.entity.User;
import club.inq.team1.repository.CommentRepository;
import club.inq.team1.repository.PostRepository;
import club.inq.team1.repository.post.CommentLikeRepository;
import club.inq.team1.service.post.CommentService;
import club.inq.team1.service.post.ReplyService;
import club.inq.team1.util.CurrentUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CurrentUser currentUser;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReplyService replyService;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public Boolean deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        User user = currentUser.get();

        if(!comment.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다.");
        }

        commentRepository.delete(comment);

        return true;
    }

    @Override
    @Transactional
    public Boolean updateComment(Long commentId, RequestCommentUpdateDTO requestCommentUpdateDTO) {
        User user = currentUser.get();
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        if(!comment.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다.");
        }

        comment.setContent(requestCommentUpdateDTO.getContent());
        commentRepository.save(comment);

        return true;
    }

    @Override
    @Transactional
    public Boolean createComment(RequestCommentCreateDTO dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow();
        User user = currentUser.get();

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(dto.getContent());

        commentRepository.save(comment);

        return true;
    }

    @Override
    @Transactional
    public Boolean toggleCommentLike(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        User user = currentUser.get();

        Optional<CommentLike> alreadyLikeComment = commentLikeRepository.findByUserAndComment(user, comment);

        if(alreadyLikeComment.isPresent()){
            commentLikeRepository.delete(alreadyLikeComment.get());
            return false;
        }

        CommentLike commentLike = new CommentLike();
        commentLike.setComment(comment);
        commentLike.setUser(user);
        commentLikeRepository.save(commentLike);

        return true;
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
