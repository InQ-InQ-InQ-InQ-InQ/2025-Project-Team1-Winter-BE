package club.inq.team1.repository.post;

import club.inq.team1.entity.Comment;
import club.inq.team1.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost(Long post, Pageable pageable);
    Page<Comment> findByPost(Post post, Pageable pageable);
}
