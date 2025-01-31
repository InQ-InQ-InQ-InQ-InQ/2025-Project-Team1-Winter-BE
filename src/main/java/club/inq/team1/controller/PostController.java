package club.inq.team1.controller;

import club.inq.team1.dto.CommentRequestDto;
import club.inq.team1.dto.PostRequestDto;
import club.inq.team1.dto.PostResponseDto;
import club.inq.team1.entity.Comment;
import club.inq.team1.entity.Post;
import club.inq.team1.service.CommentService;
import club.inq.team1.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/new")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto,
                                                      @RequestParam Long user_id) {
        PostResponseDto  createdPost = postService.createPost(requestDto, user_id);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto post = postService.getPostById(id); // PostResponseDto를 바로 반환
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<String> getPostImageById(@PathVariable Long id) {
        String imageUrl = postService.getPostImageById(id);
        return ResponseEntity.ok(imageUrl);
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long id) {
        List<Comment> comments = postService.getCommentsByPostId(id);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> searchPosts(@RequestParam String q) {
        List<PostResponseDto> results = postService.searchPosts(q);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/searchByTag")
    public ResponseEntity<List<PostResponseDto>> searchByTag(@RequestParam String tag) {
        List<PostResponseDto> results = postService.searchByTag(tag);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/{id}/heart")
    public ResponseEntity<Void> toggleLike(@PathVariable Long id) {
        postService.toggleLike(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comment/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/comment/{commentId}/update")
//    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestParam String content) {
//        commentService.updateComment(commentId, content);
//        return ResponseEntity.ok().build();
//    }

    @PutMapping("/comment/{comment_id}/update")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long comment_id,
            @RequestBody CommentRequestDto requestDto) {  // ✅ JSON Body로 데이터를 받음
        Comment updatedComment = commentService.updateComment(comment_id, requestDto.getContent());
        return ResponseEntity.ok(updatedComment);
    }


}
