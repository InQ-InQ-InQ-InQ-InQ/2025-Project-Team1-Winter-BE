package club.inq.team1.dto.request.post.post;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostCreateDTO {
    private String title;
    private String content;
    private String tags;
    private BigDecimal latitude;
    private BigDecimal longitude;
    // private List<String> tags;?
}
