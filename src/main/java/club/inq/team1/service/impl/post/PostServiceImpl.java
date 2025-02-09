package club.inq.team1.service.impl.post;

import club.inq.team1.constant.ImagePath;
import club.inq.team1.dto.request.post.post.RequestPostCreateDTO;
import club.inq.team1.dto.request.post.post.RequestPostUpdateDTO;
import club.inq.team1.dto.response.post.ResponseCommentDTO;
import club.inq.team1.dto.response.post.ResponseImageDTO;
import club.inq.team1.dto.response.post.ResponsePostDTO;
import club.inq.team1.dto.response.post.ResponsePostOutlineDTO;
import club.inq.team1.dto.response.post.ResponseReplyDTO;
import club.inq.team1.entity.Comment;
import club.inq.team1.entity.Image;
import club.inq.team1.entity.Post;
import club.inq.team1.entity.Reply;
import club.inq.team1.entity.User;
import club.inq.team1.repository.CommentRepository;
import club.inq.team1.repository.PostRepository;
import club.inq.team1.repository.post.CommentLikeRepository;
import club.inq.team1.repository.post.ImageRepository;
import club.inq.team1.repository.post.PostLikeRepository;
import club.inq.team1.repository.post.ReplyLikeRepository;
import club.inq.team1.service.post.CommentService;
import club.inq.team1.service.post.ImageService;
import club.inq.team1.service.post.PostService;
import club.inq.team1.service.post.ReplyService;
import club.inq.team1.util.CurrentUser;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    public Boolean createPost(RequestPostCreateDTO requestPostCreateDTO, List<MultipartFile> multipartFiles) {
        User user = currentUser.get(); // 작성자 조회

        Post post = new Post();
        post.setTitle(requestPostCreateDTO.getTitle());
        post.setUser(user);
        post.setContent(requestPostCreateDTO.getContent());
        post.setTags(requestPostCreateDTO.getTags());

        postRepository.save(post);

        multipartFiles.forEach(file -> imageService.saveWithPost(file, post));

        return true;
    }

    @Override
    public Boolean updatePost(RequestPostUpdateDTO requestPostUpdateDTO, List<MultipartFile> multipartFiles) {
        return null;
    }

    @Override
    @Transactional
    public Page<ResponsePostOutlineDTO> getAllPostWithPaging(String query, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContainingOrContentContaining(query,
                query, pageable);

        return posts.map(this::toResponsePostOutlineDTO);
    }

    @Override
    public ResponsePostDTO getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
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
        dto.setMyLike(postLikeRepository.existsByUserAndPost(user,post));
        dto.setCreatedAt(post.getCreatedAt());
        dto.setModifiedAt(post.getModifiedAt());
        dto.setImages(post.getImages().stream().map(imageService::toResponseImageDTO).toList());
        dto.setComments(post.getComments().stream().map(commentService::toResponseCommentDTO).toList());

        return dto;
    }

    private ResponsePostOutlineDTO toResponsePostOutlineDTO(Post post) {
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
