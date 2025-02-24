package club.inq.team1.service.impl.user;

import club.inq.team1.dto.response.user.ResponseMailDetailDTO;
import club.inq.team1.service.user.MailService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Override
    public Slice<ResponseMailDetailDTO> getMails(Pageable pageable) {
        return null;
    }
}
