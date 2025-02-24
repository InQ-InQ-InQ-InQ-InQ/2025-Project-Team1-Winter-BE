package club.inq.team1.repository.post;

import club.inq.team1.entity.Image;
import club.inq.team1.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPost(Post post);
}
