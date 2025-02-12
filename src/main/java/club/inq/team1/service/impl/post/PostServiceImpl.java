package club.inq.team1.service.impl.post;

import club.inq.team1.dto.request.post.post.RequestPostCreateDTO;
import club.inq.team1.dto.request.post.post.RequestPostUpdateDTO;
import club.inq.team1.dto.response.post.ResponsePostDTO;
import club.inq.team1.dto.response.post.ResponsePostOutlineDTO;
import club.inq.team1.entity.Image;
import club.inq.team1.entity.Post;
import club.inq.team1.entity.PostLike;
import club.inq.team1.entity.User;
import club.inq.team1.repository.PostRepository;
import club.inq.team1.repository.post.PostLikeRepository;
import club.inq.team1.service.post.CommentService;
import club.inq.team1.service.post.ImageService;
import club.inq.team1.service.post.PostService;
import club.inq.team1.util.CurrentUser;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final CurrentUser currentUser;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final ImageService imageService;
    private final CommentService commentService;

    @Override
    @Transactional
    public ResponsePostDTO createPost(RequestPostCreateDTO requestPostCreateDTO, List<MultipartFile> multipartFiles) {
        Post post = toPost(requestPostCreateDTO);
        Post save = postRepository.save(post);

        List<Image> saved = imageService.saveWithPost(multipartFiles, post);
//        save.setImages(saved); // 이미지를 연결하는 과정인데 쿼리가 하나 더 나가기 때문에 일단 안쓰는 방향으로 설정함.

        return toResponsePostDTO(save);
    }

    @Override
    @Transactional
    public Boolean updatePost(Long postId, RequestPostUpdateDTO requestPostUpdateDTO, List<MultipartFile> multipartFiles) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = currentUser.get();
        if(!post.getUser().getUserId().equals(user.getUserId())){
            return false;
        }

        post.setTitle(requestPostUpdateDTO.getTitle());
        post.setContent(requestPostUpdateDTO.getContent());
        post.setTags(requestPostUpdateDTO.getTags());
        post.setLatitude(requestPostUpdateDTO.getLatitude());
        post.setLongitude(requestPostUpdateDTO.getLongitude());

        postRepository.save(post);

        return true;
    }

    @Override
    @Transactional
    public Page<ResponsePostOutlineDTO> searchPost(String query, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContainingOrContentContaining(query,
                query, pageable);

        return posts.map(this::toResponsePostOutlineDTO);
    }

    @Override
    public Boolean deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = currentUser.get();

        if(!post.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("본인의 게시글이 아닙니다.");
        }

        // 게시글 관련 정보 지워야됨. 댓글은 알아서 지워지는데 답글은 안지워짐 이미지 정보도 지워지는데 이미지 파일은 안지워짐
        postRepository.delete(post);

        return true;
    }

    @Override
    public ResponsePostDTO getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return toResponsePostDTO(post);
    }

    private Post toPost(RequestPostCreateDTO requestPostCreateDTO) {
        Post post = new Post();
        User user = currentUser.get(); // 작성자 조회

        post.setTitle(requestPostCreateDTO.getTitle());
        post.setUser(user);
        post.setContent(requestPostCreateDTO.getContent());
        post.setTags(requestPostCreateDTO.getTags());
        post.setLatitude(requestPostCreateDTO.getLatitude());
        post.setLongitude(requestPostCreateDTO.getLongitude());

        return post;
    }

    @Override
    public Page<ResponsePostOutlineDTO> tagSearchPost(String tag, Pageable pageable) {
        Page<Post> posts = postRepository.findByTagsContaining(tag, pageable);

        return posts.map(this::toResponsePostOutlineDTO);
    }

    @Override
    public Boolean togglePostLike(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = currentUser.get();

        Optional<PostLike> alreadyLikePost = postLikeRepository.findByUserAndPost(user, post);

        if(alreadyLikePost.isPresent()){
            postLikeRepository.delete(alreadyLikePost.get());
            return false; // 현재 상태는 false로 바뀜.
        }

        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setUser(user);
        postLikeRepository.save(postLike);
        return true; // 현재 상태는 true.
    }

    @Override
    public ResponsePostDTO toResponsePostDTO(Post post) {
        User user = currentUser.get();

        ResponsePostDTO dto = new ResponsePostDTO();

        dto.setPostId(post.getPostId());
        dto.setUserId(post.getUser().getUserId());
        dto.setNickname(post.getUser().getUserInfo().getNickname());
        dto.setMyPost(post.getUser().getUserId().equals(user.getUserId()));
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setTags(post.getTags());
        dto.setRegion(post.getRegion());
        dto.setLatitude(post.getLatitude());
        dto.setLongitude(post.getLongitude());
        dto.setPostLikeCount(post.getPostLikes().size());
        dto.setMyLike(postLikeRepository.existsByUserAndPost(user, post));
        dto.setCreatedAt(post.getCreatedAt());
        dto.setModifiedAt(post.getModifiedAt());
        dto.setImages(post.getImages().stream().map(imageService::toResponseImageDTO).toList());
        dto.setComments(post.getComments().stream().map(commentService::toResponseCommentDTO).toList());

        return dto;
    }

    @Override
    public ResponsePostOutlineDTO toResponsePostOutlineDTO(Post post) {
        User user = currentUser.get();

        ResponsePostOutlineDTO dto = new ResponsePostOutlineDTO();

        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setUserId(post.getUser().getUserId());
        dto.setNickname(post.getUser().getUserInfo().getNickname());
        dto.setRegion(post.getRegion());
        dto.setLatitude(post.getLatitude());
        dto.setLongitude(post.getLongitude());
        dto.setMyLike(postLikeRepository.existsByUserAndPost(user, post));
        dto.setTags(post.getTags());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setModifiedAt(post.getModifiedAt());
        dto.setPostLikeCount(post.getPostLikes().size());
        dto.setImagePath(post.getImages().get(0).getImagePath());

        return dto;
    }
}
