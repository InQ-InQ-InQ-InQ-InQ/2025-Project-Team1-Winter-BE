package club.inq.team1.dto.request.post.post;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestPostCreateDTO {
    private String title;
    private String content;
    private String tags;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private List<MultipartFile> files;
    // private List<String> tags;?
}
