package club.inq.team1.controller;

import club.inq.team1.service.post.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;


}
