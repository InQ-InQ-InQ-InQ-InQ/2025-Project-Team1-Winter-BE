package club.inq.team1.repository;

import club.inq.team1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
    List<Post> findByTagsContaining(String tag);
}
