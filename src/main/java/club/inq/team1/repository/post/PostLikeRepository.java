package club.inq.team1.repository.post;

import club.inq.team1.entity.Post;
import club.inq.team1.entity.PostLike;
import club.inq.team1.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    /**
     * 해당 유저가 게시글에 좋아요를 눌렀는지 아닌지 확인한다.
     *
     * @param user 확인할 유저
     * @param post 확인할 게시글
     * @return 유저가 게시글에 좋아요를 눌렀다면 true, 아니라면 false
     */
    Boolean existsByUserAndPost(User user, Post post);

    /**
     * 해당 유저가 게시글에 좋아요를 눌렀는지 아닌지 확인한다.
     *
     * @param user 확인할 유저
     * @param post 확인할 게시글
     * @return 유저가 게시글에 좋아요를 눌렀다면 엔티티가 포함되고, 아니라면 empty가 리턴.
     */
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
