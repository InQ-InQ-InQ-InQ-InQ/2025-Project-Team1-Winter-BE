package club.inq.team1.service.post;

import club.inq.team1.dto.response.post.ResponseImageDTO;
import club.inq.team1.entity.Image;
import club.inq.team1.entity.Post;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ResponseImageDTO toResponseImageDTO(Image image);

    Boolean saveWithPost(MultipartFile file, Post post);
}
