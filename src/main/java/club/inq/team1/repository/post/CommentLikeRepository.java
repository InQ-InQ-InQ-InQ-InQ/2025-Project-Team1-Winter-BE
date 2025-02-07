package club.inq.team1.repository.post;

import club.inq.team1.entity.Comment;
import club.inq.team1.entity.CommentLike;
import club.inq.team1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    /**
     * 해당 유저가 해당 댓글에 좋아요를 눌렀는지 확인한다.
     *
     * @param user    확인할 유저
     * @param comment 확인할 댓글
     * @return 좋아요를 눌렀다면 true, 아니라면 false
     */
    Boolean existsByUserAndComment(User user, Comment comment);
}
