package club.inq.team1.controller;

import club.inq.team1.dto.request.post.comment.RequestCommentCreateDTO;
import club.inq.team1.dto.request.post.comment.RequestCommentUpdateDTO;
import club.inq.team1.service.post.CommentService;
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
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/new")
    public ResponseEntity<Boolean> createComment(@RequestBody RequestCommentCreateDTO requestCommentCreateDTO){
        Boolean success = commentService.createComment(requestCommentCreateDTO);

        return ResponseEntity.ok(success);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Boolean> updateComment(@PathVariable("commentId") Long commentId,
                                 @RequestBody RequestCommentUpdateDTO requestCommentUpdateDTO){
        Boolean success = commentService.updateComment(commentId, requestCommentUpdateDTO);

        return ResponseEntity.ok(success);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable("commentId") Long commentId){
        Boolean success = commentService.deleteComment(commentId);

        return ResponseEntity.ok(success);
    }
}
