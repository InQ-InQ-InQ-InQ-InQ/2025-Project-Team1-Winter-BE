package club.inq.team1.constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum ImagePath {
    WINDOW("C:/images/"),
    LINUX("home/user/images/"),
    SAVE_PROFILE("profile/" + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "/");
    private final String path;

    ImagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return WINDOW.path + path;
    }
}
