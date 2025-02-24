package club.inq.team1.service.user;

import club.inq.team1.dto.response.user.ResponseMailDetailDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MailService {
    Slice<ResponseMailDetailDTO> getMails(Pageable pageable);
}
