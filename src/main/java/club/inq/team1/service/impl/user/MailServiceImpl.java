package club.inq.team1.service.impl.user;

import club.inq.team1.dto.response.user.ResponseMailDetailDTO;
import club.inq.team1.entity.Mail;
import club.inq.team1.entity.Post;
import club.inq.team1.entity.User;
import club.inq.team1.repository.user.MailRepository;
import club.inq.team1.service.user.MailService;
import club.inq.team1.util.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailRepository mailRepository;
    private final CurrentUser currentUser;

    @Override
    public Slice<ResponseMailDetailDTO> getMails(Pageable pageable) {
        User user = currentUser.get();
        Slice<Mail> mails = mailRepository.findByUser(user, pageable);
        return mails.map(this::toResponseMailDetailDTO);
    }

    @Override
    public ResponseMailDetailDTO toResponseMailDetailDTO(Mail mail) {
        ResponseMailDetailDTO dto = new ResponseMailDetailDTO();
        Post post = mail.getPost();
        User writer = post.getUser();

        dto.setPostId(post.getPostId());
        dto.setUserId(writer.getUserId());
        dto.setNickname(writer.getUserInfo().getNickname());
        dto.setTitle(post.getTitle());
        dto.setImagePath(post.getImages().get(0).getImagePath());
        dto.setCreatedAt(mail.getCreatedAt());
        dto.setSaw(mail.getSaw());
        return dto;
    }

    @Override
    @Transactional
    public Boolean toggleMailSaw(Long mailId) {
        User user = currentUser.get();
        Mail mail = mailRepository.findById(mailId).orElseThrow();
        if(!mail.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("본인 알림만 확인할 수 있습니다.");
        }

        mail.setSaw(!mail.getSaw());
        mailRepository.save(mail);

        return mail.getSaw();
    }

    @Override
    @Transactional
    public Boolean deleteMail(Long mailId) {
        User user = currentUser.get();
        Mail mail = mailRepository.findById(mailId).orElseThrow();

        if(!mail.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("본인 알림만 삭제할 수 있습니다.");
        }

        mailRepository.delete(mail);

        return true;
    }
}
