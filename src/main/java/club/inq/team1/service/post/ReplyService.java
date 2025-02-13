package club.inq.team1.service.post;

import club.inq.team1.dto.request.post.reply.RequestReplyCreateDTO;
import club.inq.team1.dto.request.post.reply.RequestReplyUpdateDTO;
import club.inq.team1.dto.response.post.ResponseReplyDTO;
import club.inq.team1.entity.Reply;

public interface ReplyService {
    
    /**
     * 답글을 작성한다.
     * 
     * @param requestReplyCreateDTO 답글에 대한 데이터
     * @return 답글 작성 성공 여부
     */
    Boolean createReply(RequestReplyCreateDTO requestReplyCreateDTO);

    /**
     * 답글을 수정한다.
     *
     * @return 답글 수정 성공 여부
     */
    Boolean updateReply(Long replyId, RequestReplyUpdateDTO requestReplyUpdateDTO);

    /**
     * 답글을 제거한다.
     *
     * @param replyId 제거할 답글의 고유 아이디
     * @return 답글 제거 성공 여부
     */
    Boolean deleteReply(Long replyId);

    /**
     * 답글에 좋아요를 토글 형태로 변화시킨다. ex) 좋아요를 누른 상태였다면 취소, 아니라면 좋아요.
     *
     * @param replyId 답글 데이터
     * @return 토글 된 뒤 좋아요 상태
     */
    Boolean toggleReplyLike(Long replyId);

    ResponseReplyDTO toResponseReplyDTO(Reply reply);
}
