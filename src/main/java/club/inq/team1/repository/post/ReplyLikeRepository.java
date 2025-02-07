package club.inq.team1.repository.post;

import club.inq.team1.entity.Reply;
import club.inq.team1.entity.ReplyLike;
import club.inq.team1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {

    /**
     * 해당 유저가 해당 답글에 좋아요를 눌렀는지 확인한다.
     *
     * @param user  확인할 유저
     * @param reply 확인할 답글
     * @return 좋아요를 눌렀다면 true, 아니라면 false
     */
    Boolean existsByUserAndReply(User user, Reply reply);
}
