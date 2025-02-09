package club.inq.team1.service;

import club.inq.team1.dto.CommentRequestDto;
import club.inq.team1.entity.Comment;
import club.inq.team1.entity.Post;
import club.inq.team1.entity.User;
import club.inq.team1.repository.CommentRepository;
import club.inq.team1.repository.PostRepository;
import club.inq.team1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        comment.setContent(newContent);
        // updatedAt 필드 자동으로 갱신시킴
        // save?
        return comment;
    }

    @Transactional
    public Comment createComment(Long postId, Long userId, CommentRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());  // DTO에서 받은 content 설정
        comment.setPost(post);
        comment.setUser(user);
        // save?

        return commentRepository.save(comment);
    }


}
