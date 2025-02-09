package club.inq.team1.service.impl.post;

import club.inq.team1.dto.request.post.post.RequestPostCreateDTO;
import club.inq.team1.dto.request.post.post.RequestPostDeleteDTO;
import club.inq.team1.dto.request.post.post.RequestPostUpdateDTO;
import club.inq.team1.dto.response.post.ResponsePostDTO;
import club.inq.team1.dto.response.post.ResponsePostOutlineDTO;
import club.inq.team1.entity.Post;
import club.inq.team1.entity.User;
import club.inq.team1.repository.PostRepository;
import club.inq.team1.repository.post.PostLikeRepository;
import club.inq.team1.service.post.CommentService;
import club.inq.team1.service.post.ImageService;
import club.inq.team1.service.post.PostService;
import club.inq.team1.util.CurrentUser;
import java.util.List;
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
        User user = currentUser.get(); // 작성자 조회

        Post post = toPost(requestPostCreateDTO, user);
        Post save = postRepository.save(post);

        multipartFiles.forEach(file -> imageService.saveWithPost(file, save));

        return toResponsePostDTO(post);
    }

    @Override
    @Transactional
    public Boolean updatePost(RequestPostUpdateDTO requestPostUpdateDTO, List<MultipartFile> multipartFiles) {
        Post post = postRepository.findById(requestPostUpdateDTO.getPostId()).orElseThrow();
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
    public Page<ResponsePostOutlineDTO> getAllPostWithPaging(String query, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContainingOrContentContaining(query,
                query, pageable);

        return posts.map(this::toResponsePostOutlineDTO);
    }

    @Override
    public Boolean deletePost(RequestPostDeleteDTO requestPostDeleteDTO) {
        Post post = postRepository.findById(requestPostDeleteDTO.getPostId()).orElseThrow();
        User user = currentUser.get();

        // 게시글 관련 정보 지워야됨. 댓글은 알아서 지워지는데 답글은 안지워짐 이미지 정보도 지워지는데 이미지 파일은 안지워짐


        return true;
    }

    @Override
    public ResponsePostDTO getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return toResponsePostDTO(post);
    }

    private Post toPost(RequestPostCreateDTO requestPostCreateDTO, User user) {
        Post post = new Post();

        post.setTitle(requestPostCreateDTO.getTitle());
        post.setUser(user);
        post.setContent(requestPostCreateDTO.getContent());
        post.setTags(requestPostCreateDTO.getTags());
        post.setLatitude(requestPostCreateDTO.getLatitude());
        post.setLongitude(requestPostCreateDTO.getLongitude());

        return post;
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
        dto.setTitle(post.getTags());
        dto.setWhere(post.getWhere());
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
        ResponsePostOutlineDTO dto = new ResponsePostOutlineDTO();

        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setUserId(post.getUser().getUserId());
        dto.setNickname(post.getUser().getUserInfo().getNickname());
        dto.setImagePath(post.getImages().get(0).getImagePath());
        dto.setTags(post.getTags());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setModifiedAt(post.getModifiedAt());
        dto.setPostLikeCount(post.getPostLikes().size());

        return dto;
    }
}
