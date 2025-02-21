package club.inq.team1.controller;

import club.inq.team1.dto.request.post.comment.RequestCommentCreateDTO;
import club.inq.team1.dto.request.post.comment.RequestCommentUpdateDTO;
import club.inq.team1.service.post.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "/api/comments", description = "댓글 관련")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/new")
    @Operation(summary = "댓글 작성", responses = {
            @ApiResponse(responseCode = "200", description = "댓글 작성 완료")
    })
    public ResponseEntity<Boolean> createComment(@RequestBody RequestCommentCreateDTO requestCommentCreateDTO){
        Boolean success = commentService.createComment(requestCommentCreateDTO);

        return ResponseEntity.ok(success);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정", responses = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 완료")
    })
    public ResponseEntity<Boolean> updateComment(@PathVariable("commentId") Long commentId,
                                 @RequestBody RequestCommentUpdateDTO requestCommentUpdateDTO){
        Boolean success = commentService.updateComment(commentId, requestCommentUpdateDTO);

        return ResponseEntity.ok(success);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 완료")
    })
    public ResponseEntity<Boolean> deleteComment(@PathVariable("commentId") Long commentId){
        Boolean success = commentService.deleteComment(commentId);

        return ResponseEntity.ok(success);
    }

    @PostMapping("/{commentId}/heart")
    @Operation(summary = "댓글 좋아요 상태 변경", responses = {
            @ApiResponse(responseCode = "200", description = "좋아요 상태 변경 완료")
    })
    public ResponseEntity<Boolean> toggleCommentLike(@PathVariable("commentId") Long commentId){
        Boolean success = commentService.toggleCommentLike(commentId);

        return ResponseEntity.ok(success);
    }
}
