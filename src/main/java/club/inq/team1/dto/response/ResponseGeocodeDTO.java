package club.inq.team1.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * 네이버 Geocoding api response 변환 DTO
 * https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode
 * 위 링크에서 응답 양식 확인 가능
 */

@Getter
@Component
public class ResponseGeocodeDTO {

    @JsonProperty("status")
    private String status;

    @JsonProperty("meta")
    private Object meta;

    @JsonProperty("meta.totalCount")
    private Integer totalCount;

    @JsonProperty("meta.page")
    private Integer nowPage;

    @JsonProperty("meta.count")
    private Integer nowPageCount;

    @JsonProperty("addresses")
    private List<Address> addresses;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @Getter
    public static class Address{

        @JsonProperty("roadAddress")
        private String roadAddress;  // 도로명 주소

        @JsonProperty("jibunAddress")
        private String jibunAddress;  // 지번 주소

        @JsonProperty("englishAddress")
        private String englishAddress;  // 영문 주소

        @JsonProperty("addressElements")
        private List<AddressElement> addressElements;  // 주소 요소들 (여러 개의 AddressElement)

        @JsonProperty("x")
        private String x;  // X 좌표

        @JsonProperty("y")
        private String y;  // Y 좌표

        @JsonProperty("distance")
        private Double distance;  // 거리

    }

    @Getter
    public static class AddressElement{
        @JsonProperty("types")
        private List<String> types;  // 주소 타입들 (예: "SIDO", "SIGUGUN" 등)

        @JsonProperty("longName")
        private String longName;  // 길 이름 (예: "경기도")

        @JsonProperty("shortName")
        private String shortName;  // 짧은 이름 (예: "경기")

        @JsonProperty("code")
        private String code;  // 코드
    }


}
