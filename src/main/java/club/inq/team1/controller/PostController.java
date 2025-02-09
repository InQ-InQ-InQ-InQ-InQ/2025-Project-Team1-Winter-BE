package club.inq.team1.controller;

import club.inq.team1.dto.request.post.post.RequestPostCreateDTO;
import club.inq.team1.dto.response.post.ResponsePostDTO;
import club.inq.team1.service.post.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<ResponsePostDTO> createPost(@RequestBody RequestPostCreateDTO dto,
                                                      @RequestPart(name = "image") List<MultipartFile> files){
        ResponsePostDTO post = postService.createPost(dto, files);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePostDTO> getPost(@PathVariable("postId") Long postId){
        ResponsePostDTO post = postService.getPost(postId);

        return ResponseEntity.ok(post);
    }

    
}
