package club.inq.team1.service.impl.post;

import club.inq.team1.constant.ImagePath;
import club.inq.team1.dto.response.post.ResponseImageDTO;
import club.inq.team1.entity.Image;
import club.inq.team1.entity.Post;
import club.inq.team1.repository.post.ImageRepository;
import club.inq.team1.service.post.ImageService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public ResponseImageDTO toResponseImageDTO(Image image) {
        ResponseImageDTO dto = new ResponseImageDTO();

        dto.setImageId(image.getImageId());
        dto.setPostId(image.getPost().getPostId());
        dto.setImagePath(image.getImagePath());
        dto.setOriginalName(image.getOriginalName());

        return dto;
    }

    @Override
    @Transactional
    public List<Image> saveWithPost(List<MultipartFile> files, Post post) {
        List<Image> images = new ArrayList<>();

        String defaultStoredPath = ImagePath.WINDOW.getPath();
        String postImageStoredPath = ImagePath.SAVE_POST.getPath();

        for(MultipartFile file:files) {
            String storedName = UUID.randomUUID() + file.getOriginalFilename();
            String storedPath = defaultStoredPath + postImageStoredPath + storedName;
            File stored = Path.of(storedPath).toFile();
            try {
                stored.mkdirs();
                file.transferTo(stored);
            } catch (IOException e) {
                throw new RuntimeException("이미지 저장 과정에서 문제가 발생했습니다.");
            }

            Image image = new Image();
            image.setPost(post);
            image.setOriginalName(file.getOriginalFilename());
            image.setImagePath(postImageStoredPath + storedName);
            images.add(image);
        }

        List<Image> saved = imageRepository.saveAll(images);

        return saved;
    }


}
