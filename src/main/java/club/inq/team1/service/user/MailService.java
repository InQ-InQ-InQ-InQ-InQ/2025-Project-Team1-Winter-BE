package club.inq.team1.service.user;

import club.inq.team1.dto.response.user.ResponseMailDetailDTO;
import club.inq.team1.entity.Mail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MailService {
    Slice<ResponseMailDetailDTO> getMails(Pageable pageable);

    ResponseMailDetailDTO toResponseMailDetailDTO(Mail mail);
}
