package club.inq.team1.dto;

import club.inq.team1.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    // Getters
    private final Long id;
    private final String title;
    private final String content;
    private final String username;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
    }

}
