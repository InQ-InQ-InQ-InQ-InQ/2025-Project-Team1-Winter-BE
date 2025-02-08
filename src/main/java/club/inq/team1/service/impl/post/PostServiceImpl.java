package club.inq.team1.service.impl.post;

import club.inq.team1.constant.ImagePath;
import club.inq.team1.dto.request.post.post.RequestPostCreateDTO;
import club.inq.team1.dto.request.post.post.RequestPostUpdateDTO;
import club.inq.team1.entity.Image;
import club.inq.team1.entity.Post;
import club.inq.team1.entity.User;
import club.inq.team1.repository.PostRepository;
import club.inq.team1.repository.post.ImageRepository;
import club.inq.team1.service.post.PostService;
import club.inq.team1.util.CurrentUser;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
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
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final CurrentUser currentUser;

    @Override
    @Transactional
    public Boolean createPost(RequestPostCreateDTO requestPostCreateDTO, List<MultipartFile> multipartFiles) {
        User user = currentUser.get(); // 작성자 조회

        Post post = new Post();
        post.setTitle(requestPostCreateDTO.getTitle());
        post.setUser(user);
        post.setContent(requestPostCreateDTO.getContent());
        post.setTags(requestPostCreateDTO.getTags());

        Post save = postRepository.save(post);
        post.setImages(toImage(multipartFiles, post));

        postRepository.save(save);

        return true;
    }

    @Override
    public Boolean updatePost(RequestPostUpdateDTO requestPostUpdateDTO, List<MultipartFile> multipartFiles) {
        return null;
    }

    @Transactional
    private ArrayList<Image> toImage(List<MultipartFile> multipartFiles, Post post){
        ArrayList<Image> images = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String defaultStoredPath = ImagePath.WINDOW.getPath();
            String postImageStoredPath = ImagePath.SAVE_POST.getPath();
            String storedName = UUID.randomUUID() + file.getOriginalFilename();
            String storedPath = defaultStoredPath + postImageStoredPath + storedName;
            File stored = Path.of(storedPath).toFile();
            try {
                file.transferTo(stored);
            } catch (IOException e) {
                throw new RuntimeException("이미지 저장 과정에서 문제가 발생했습니다.");
            }

            Image image = new Image();
            image.setPost(post);
            image.setOriginalName(file.getOriginalFilename());
            image.setImagePath(postImageStoredPath + storedName);

            Image save = imageRepository.save(image);
            images.add(save);
        }

        return images;
    }
}
