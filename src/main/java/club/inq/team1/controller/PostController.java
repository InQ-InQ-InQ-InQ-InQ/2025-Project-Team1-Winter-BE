package club.inq.team1.controller;

import club.inq.team1.dto.request.post.post.RequestPostCreateDTO;
import club.inq.team1.dto.response.post.ResponsePostDTO;
import club.inq.team1.dto.response.post.ResponsePostOutlineDTO;
import club.inq.team1.service.post.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponsePostDTO> createPost(@ModelAttribute RequestPostCreateDTO dto) {
        log.info(dto.toString());
        ResponsePostDTO post = postService.createPost(dto, dto.getFiles());
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePostDTO> getPost(@PathVariable("postId") Long postId) {
        ResponsePostDTO post = postService.getPost(postId);

        return ResponseEntity.ok(post);
    }

    /**
     * 제목이나 내용으로 검색
     *
     * @param query    제목이나 내용에 포함됐으면 하는 내용
     * @param pageable {@link club.inq.team1.entity.Post} 페이징. 기본 값은 24개,
     * @return 페이징 처리 된 {@link ResponsePostOutlineDTO} 의 List
     */
    @GetMapping("/search")
    public ResponseEntity<Page<ResponsePostOutlineDTO>> searchPost(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @PageableDefault(size = 24, sort = {"postId"}, direction = Direction.DESC)
            Pageable pageable) {
        Page<ResponsePostOutlineDTO> responsePostOutlineDTOS = postService.searchPost(query, pageable);

        return ResponseEntity.ok(responsePostOutlineDTOS);
    }

    @GetMapping("/tag-search")
    public ResponseEntity<Page<ResponsePostOutlineDTO>> tagSearchPost(
            @RequestParam(value = "tag", required = true) String tag,
            @PageableDefault() Pageable pageable) {
        Page<ResponsePostOutlineDTO> responsePostOutlineDTOS = postService.tagSearchPost(tag, pageable);

        return ResponseEntity.ok(responsePostOutlineDTOS);
    }
}
