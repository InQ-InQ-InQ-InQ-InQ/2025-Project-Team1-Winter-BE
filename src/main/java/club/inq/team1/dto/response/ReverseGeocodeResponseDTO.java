package club.inq.team1.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ReverseGeocodeResponseDTO {

    @JsonProperty("status")
    private Status status;

    @JsonProperty("results")
    private List<Result> results;

    @Getter
    public static class Status {

        @JsonProperty("code")
        private String code;  // 예: 0

        @JsonProperty("name")
        private String name;  // 예: OK

        @JsonProperty("message")
        private String message;  // 에러메시지
    }

    @Getter
    public static class Result {
        @JsonProperty("region")
        private Region region;
    }

    @Getter
    public static class Region {

        @JsonProperty("area0")
        private Area area0;  // 예: "kr" (국가)

        @JsonProperty("area1")
        private Area area1;  // 예: 서울, 경기도 (도시)

        @JsonProperty("area2")
        private Area area2;  // 예: 송파구, 수원시 (시/구/군)
    }

    @Getter
    public static class Area {
        @JsonProperty("name")
        private String name; // 예 : 서울특별시, 경기도

        @JsonProperty("alias")
        private String alias; // 예 : 서울, 경기
    }


}

