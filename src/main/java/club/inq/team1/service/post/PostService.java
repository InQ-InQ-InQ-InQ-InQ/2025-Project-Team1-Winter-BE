package club.inq.team1.service.post;

import club.inq.team1.dto.request.post.post.RequestPostCreateDTO;
import club.inq.team1.dto.request.post.post.RequestPostUpdateDTO;
import club.inq.team1.dto.response.post.ResponsePostDTO;
import club.inq.team1.dto.response.post.ResponsePostOutlineDTO;
import club.inq.team1.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    /**
     * 게시글 작성을 위한 데이터와 이미지를 가지고 게시글을 영속화한다.
     *
     * @param requestPostCreateDTO 작성할 게시글에 대한 데이터
     * @param multipartFiles       이미지
     * @return 게시글 작성 성공 여부
     */
    ResponsePostDTO createPost(RequestPostCreateDTO requestPostCreateDTO, List<MultipartFile> multipartFiles);

    /**
     * 게시글 수정을 위한 데이터와 이미지를 가지고 영속화된 게시글 데이터를 수정한다.
     *
     * @param postId
     * @param requestPostUpdateDTO 게시글 수정을 위한 데이터
     * @param multipartFiles       이미지
     * @return 게시글 수정 성공 여부
     */
    Boolean updatePost(Long postId, RequestPostUpdateDTO requestPostUpdateDTO, List<MultipartFile> multipartFiles);

    /**
     * 제목과 내용으로 게시글을 검색한다.
     *
     * @param query    제목이나 내용에서 찾으려고 하는 내용
     * @param pageable {@link club.inq.team1.entity.Post} 에 대해 페이징을 한다.
     * @return 페이징을 통해 데이터를 받는다.
     */
    Page<ResponsePostOutlineDTO> searchPost(String query, Pageable pageable);

    Boolean deletePost(Long postId);

    ResponsePostOutlineDTO toResponsePostOutlineDTO(Post post);

    ResponsePostDTO getPost(Long postId);

    ResponsePostDTO toResponsePostDTO(Post post);

    /**
     * 태그로 게시글을 검색한다.
     *
     * @param tag      검색하려고 하는 태그
     * @param pageable {@link Post} 에 대해 페이징을 한다.
     * @return 페이징을 통해 게시글 목록을 받는다.
     */
    Page<ResponsePostOutlineDTO> tagSearchPost(String tag, Pageable pageable);

    Boolean togglePostLike(Long postId);
    // 작성자 닉네임으로 검색.
}
