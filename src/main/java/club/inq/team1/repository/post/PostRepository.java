package club.inq.team1.repository.post;

import club.inq.team1.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);
    Page<Post> findByTagsContaining(String tag, Pageable pageable);
    Page<Post> findByRegionContaining(String region, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Post post SET post.hit = post.hit + 1 WHERE post.postId = :postId")
    void increaseHit(@Param("postId") Long postId);
}
