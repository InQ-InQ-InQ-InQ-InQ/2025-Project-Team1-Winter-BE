package club.inq.team1.controller;

import club.inq.team1.dto.request.post.reply.RequestReplyCreateDTO;
import club.inq.team1.dto.request.post.reply.RequestReplyUpdateDTO;
import club.inq.team1.service.post.ReplyService;
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
@RequestMapping("/api/replies")
@RequiredArgsConstructor
@Tag(name = "/api/replies", description = "답글 관련")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/new")
    @Operation(summary = "답글 작성", responses = {
            @ApiResponse(responseCode = "200", description = "답글 작성 완료")
    })
    public ResponseEntity<Boolean> createReply(@RequestBody RequestReplyCreateDTO requestReplyCreateDTO){
        Boolean success = replyService.createReply(requestReplyCreateDTO);

        return ResponseEntity.ok(success);
    }

    @PutMapping("/{replyId}")
    @Operation(summary = "답글 수정", responses = {
            @ApiResponse(responseCode = "200", description = "답글 수정 완료")
    })
    public ResponseEntity<Boolean> updateReply(@PathVariable("replyId") Long replyId, @RequestBody RequestReplyUpdateDTO requestReplyUpdateDTO){
        Boolean success = replyService.updateReply(replyId, requestReplyUpdateDTO);

        return ResponseEntity.ok(success);
    }

    @DeleteMapping("/{replyId}")
    @Operation(summary = "답글 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "답글 삭제 완료")
    })
    public ResponseEntity<Boolean> deleteReply(@PathVariable("replyId") Long replyId){
        Boolean success = replyService.deleteReply(replyId);

        return ResponseEntity.ok(success);
    }

    @PostMapping("/{replyId}/heart")
    @Operation(summary = "답글 좋아요 상태 변경", responses = {
            @ApiResponse(responseCode = "200", description = "좋아요 상태 변경 완료")
    })
    public ResponseEntity<Boolean> toggleReplyLike(@PathVariable("replyId") Long replyId){
        Boolean currentLikeStatus = replyService.toggleReplyLike(replyId);

        return ResponseEntity.ok(currentLikeStatus);
    }
}
