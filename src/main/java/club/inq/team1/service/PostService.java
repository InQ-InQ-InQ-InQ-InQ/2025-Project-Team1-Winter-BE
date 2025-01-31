package club.inq.team1.service;

import club.inq.team1.dto.PostRequestDto;
import club.inq.team1.dto.PostResponseDto;
import club.inq.team1.entity.Comment;
import club.inq.team1.entity.Post;
import club.inq.team1.entity.User;
import club.inq.team1.repository.CommentRepository;
import club.inq.team1.repository.PostRepository;
import club.inq.team1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 게시글 생성 로직
     * @param requestDto 클라이언트 요청 데이터 ( 제목, 내용 포함 )
     * @param user_id 게시글 작성자의 ID
     * @return 저장된 게시글에 대한 응답 DTO
     *
     */
    public PostResponseDto createPost(PostRequestDto requestDto, Long user_id) {
        //작성자 (User) 정보 가져오기
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        //Post 엔티티 생성
        Post post = new Post();
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        post.setUser(user);

        //게시글 저장
        Post savedPost = postRepository.save(post);

        // 저장된 게시글을 PostResponseDto로 변환하여 반환
        return new PostResponseDto(savedPost);
    }
    /**
     * 게시글 조회 로직
     * @param id 게시글 ID
     * @return 조회된 게시글에 대한 응답 DTO
     */

    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        return new PostResponseDto(post);
    }

    public String getPostImageById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id));
        return post.getImageUrl(); // 이미지 URL 반환
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<PostResponseDto> searchPosts(String query) {
        List<Post> posts = postRepository.findByTitleContainingOrContentContaining(query, query);
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    public List<PostResponseDto> searchByTag(String tag) {
        List<Post> posts = postRepository.findByTagsContaining(tag);
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void toggleLike(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        post.setLikeCount(post.getLikeCount() + 1); // 좋아요 수 증가
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        postRepository.delete(post);
    }


}
