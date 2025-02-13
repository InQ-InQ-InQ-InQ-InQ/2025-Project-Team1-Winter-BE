package club.inq.team1.service.post;

import club.inq.team1.dto.response.post.ResponseImageDTO;
import club.inq.team1.entity.Image;
import club.inq.team1.entity.Post;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ResponseImageDTO toResponseImageDTO(Image image);

    List<Image> saveWithPost(List<MultipartFile> file, Post post);
}
