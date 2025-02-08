package club.inq.team1.service.post;

import club.inq.team1.dto.request.post.post.RequestPostCreateDTO;
import club.inq.team1.dto.request.post.post.RequestPostUpdateDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    /**
     * 게시글 작성을 위한 데이터와 이미지를 가지고 게시글을 영속화한다.
     *
     * @param requestPostCreateDTO 작성할 게시글에 대한 데이터
     * @param multipartFiles       이미지
     * @return 게시글 작성 성공 여부
     */
    Boolean createPost(RequestPostCreateDTO requestPostCreateDTO, List<MultipartFile> multipartFiles);

    /**
     * 게시글 수정을 위한 데이터와 이미지를 가지고 영속화된 게시글 데이터를 수정한다.
     *
     * @param requestPostUpdateDTO 게시글 수정을 위한 데이터
     * @param multipartFiles       이미지
     * @return 게시글 수정 성공 여부
     */
    Boolean updatePost(RequestPostUpdateDTO requestPostUpdateDTO, List<MultipartFile> multipartFiles);
}
