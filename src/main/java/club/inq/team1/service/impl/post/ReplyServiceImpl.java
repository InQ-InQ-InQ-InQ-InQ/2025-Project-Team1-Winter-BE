package club.inq.team1.service.impl.post;

import club.inq.team1.dto.request.post.reply.RequestReplyCreateDTO;
import club.inq.team1.dto.request.post.reply.RequestReplyDeleteDTO;
import club.inq.team1.dto.request.post.reply.RequestReplyLikeDTO;
import club.inq.team1.dto.request.post.reply.RequestReplyUpdateDTO;
import club.inq.team1.dto.response.post.ResponseReplyDTO;
import club.inq.team1.entity.Comment;
import club.inq.team1.entity.Reply;
import club.inq.team1.entity.ReplyLike;
import club.inq.team1.entity.User;
import club.inq.team1.repository.CommentRepository;
import club.inq.team1.repository.post.ReplyLikeRepository;
import club.inq.team1.repository.post.ReplyRepository;
import club.inq.team1.service.post.ReplyService;
import club.inq.team1.util.CurrentUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;
    private final CommentRepository commentRepository;
    private final CurrentUser currentUser;

    @Override
    @Transactional
    public Boolean createReply(RequestReplyCreateDTO requestReplyCreateDTO) {
        User user = currentUser.get();
        Comment comment = commentRepository.findById(requestReplyCreateDTO.getCommentId()).orElseThrow();

        Reply reply = new Reply();
        reply.setUser(user);
        reply.setComment(comment);
        reply.setContent(requestReplyCreateDTO.getContent());
        replyRepository.save(reply);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateReply(RequestReplyUpdateDTO requestReplyUpdateDTO) {
        User user = currentUser.get();

        Reply reply = replyRepository.findById(requestReplyUpdateDTO.getReplyId()).orElseThrow();
        if(reply.getUser().getUserId().equals(user.getUserId())){
            reply.setContent(requestReplyUpdateDTO.getContent());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteReply(RequestReplyDeleteDTO requestReplyDeleteDTO) {
        User user = currentUser.get();

        Reply reply = replyRepository.findById(requestReplyDeleteDTO.getReplyId()).orElseThrow();
        if(reply.getUser().getUserId().equals(user.getUserId())){
            replyRepository.delete(reply);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean likeReply(RequestReplyLikeDTO requestReplyLikeDTO) {
        User user = currentUser.get();

        Reply reply = replyRepository.findById(requestReplyLikeDTO.getReplyId()).orElseThrow();
        Optional<ReplyLike> alreadyLike = replyLikeRepository.findByUserAndReply(user, reply);
        if(alreadyLike.isPresent()){
            replyLikeRepository.delete(alreadyLike.get());
            return false;
        }

        ReplyLike replyLike = new ReplyLike();
        replyLike.setReply(reply);
        replyLike.setUser(user);
        replyLikeRepository.save(replyLike);

        return true;
    }

    @Override
    public ResponseReplyDTO toResponseReplyDTO(Reply reply) {
        ResponseReplyDTO dto = new ResponseReplyDTO();
        User user = currentUser.get();

        dto.setReplyId(reply.getReplyId());
        dto.setUserId(reply.getUser().getUserId());
        dto.setNickname(reply.getUser().getUserInfo().getNickname());
        dto.setCommentId(reply.getComment().getId());
        dto.setMyReply(reply.getUser().getUserId().equals(user.getUserId()));
        dto.setContent(reply.getContent());
        dto.setMyLike(replyLikeRepository.existsByUserAndReply(user,reply));
        dto.setReplyLikeCount(reply.getReplyLikes().size());
        dto.setCreatedAt(reply.getCreatedAt());
        dto.setModifiedAt(reply.getModifiedAt());

        return dto;
    }
}
