package club.inq.team1.controller;

import club.inq.team1.dto.request.post.post.RequestPostCreateDTO;
import club.inq.team1.dto.response.post.ResponsePostDTO;
import club.inq.team1.dto.response.post.ResponsePostOutlineDTO;
import club.inq.team1.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "/api/posts", description = "게시글 관련")
public class PostController {

    private final PostService postService;

    /**
     * 게시글 작성
     *
     * @param dto 게시글 작성 dto
     * @return 작성한 게시글 정보
     */
    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 작성", responses = {
            @ApiResponse(responseCode = "200", description = "게시글 작성 성공")
    })
    public ResponseEntity<ResponsePostDTO> createPost(@ModelAttribute RequestPostCreateDTO dto) {
        ResponsePostDTO post = postService.createPost(dto, dto.getFiles());
        return ResponseEntity.ok(post);
    }

    /**
     * 게시글 고유 아이디로 게시글 조회
     *
     * @param postId 게시글 고유 아이디
     * @return 해당 게시글 정보
     */
    @GetMapping("/{postId}")
    @Operation(summary = "게시글 조회", responses = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공")
    })
    public ResponseEntity<ResponsePostDTO> getPost(@PathVariable("postId") Long postId) {
        ResponsePostDTO post = postService.getPost(postId);

        return ResponseEntity.ok(post);
    }

    /**
     * 제목이나 내용으로 게시글 검색
     *
     * @param query    제목이나 내용에 포함됐으면 하는 내용
     * @param pageable {@link club.inq.team1.entity.Post} 페이징. 기본 값은 24개,
     * @return 페이징 처리 된 {@link ResponsePostOutlineDTO} 의 List
     */
    @GetMapping("/search")
    @Operation(summary = "제목, 내용으로 게시글 검색", responses = {
            @ApiResponse(responseCode = "200", description = "검색 완료")
    })
    public ResponseEntity<Page<ResponsePostOutlineDTO>> searchPost(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @PageableDefault(size = 24, sort = {"postId"}, direction = Direction.DESC) Pageable pageable) {
        Page<ResponsePostOutlineDTO> responsePostOutlineDTOS = postService.searchPost(query, pageable);

        return ResponseEntity.ok(responsePostOutlineDTOS);
    }

    /**
     * 태그로 게시글 검색
     *
     * @param tag      게시글에 포함됐으면 하는 태그
     * @param pageable {@link club.inq.team1.entity.Post} 페이징. 기본 값은 24개,
     * @return 페이징 처리 된 {@link ResponsePostOutlineDTO} 의 List
     */
    @GetMapping("/tag-search")
    @Operation(summary = "태그로 게시글 검색", responses = {
            @ApiResponse(responseCode = "200", description = "검색 완료")
    })
    public ResponseEntity<Page<ResponsePostOutlineDTO>> tagSearchPost(
            @RequestParam(value = "tag", required = false, defaultValue = "") String tag,
            @PageableDefault(size = 24, sort = {"postId"}, direction = Direction.DESC) Pageable pageable) {
        Page<ResponsePostOutlineDTO> responsePostOutlineDTOS = postService.tagSearchPost(tag, pageable);

        return ResponseEntity.ok(responsePostOutlineDTOS);
    }

    /**
     * 게시글 삭제
     *
     * @param postId 게시글 고유 아이디
     * @return 게시글 삭제 성공 여부
     */
    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "삭제 완료")
    })
    public ResponseEntity<Boolean> deletePost(@PathVariable("postId") Long postId){
        Boolean delete = postService.deletePost(postId);

        return ResponseEntity.ok(delete);
    }

    /**
     * 게시글 좋아요 상태 토글 형식으로 변경
     *
     * @param postId 게시글 아이디
     * @return 현재 게시글 좋아요 상태
     */
    @PostMapping("/{postId}/heart")
    @Operation(summary = "게시글 좋아요 상태 변경", responses = {
            @ApiResponse(responseCode = "200", description = "상태 변경 완료")
    })
    public ResponseEntity<Boolean> togglePostLike(@PathVariable("postId") Long postId){
        Boolean currentMyLikeStatus = postService.togglePostLike(postId);

        return ResponseEntity.ok(currentMyLikeStatus);
    }

}
