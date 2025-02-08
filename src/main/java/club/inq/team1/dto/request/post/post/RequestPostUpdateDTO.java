package club.inq.team1.dto.request.post.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostUpdateDTO {
    private String title;
    private String content;
    private String tags;
    // private List<String> tags;?
}
