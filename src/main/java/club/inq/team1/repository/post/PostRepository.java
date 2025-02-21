package club.inq.team1.repository.post;

import club.inq.team1.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);
    Page<Post> findByTagsContaining(String tag, Pageable pageable);
}
