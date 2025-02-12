package club.inq.team1.util;

import club.inq.team1.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
    public User get() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(details instanceof User user){
            return user;
        }

        throw new RuntimeException("로그인이 필요합니다.");
    }
}
