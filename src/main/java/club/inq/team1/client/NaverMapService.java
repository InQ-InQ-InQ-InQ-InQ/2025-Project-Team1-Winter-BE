package club.inq.team1.client;

import club.inq.team1.dto.response.GeocodeResponseDTO;
import club.inq.team1.dto.response.ReverseGeocodeResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Naver Map API 와 직접적으로 통신하는 클래스입니다
 */

@Component
public class NaverMapService {

    @Value("${naver.map.client-id}")
    private String clientId;

    @Value("${naver.map.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 주소검색 -> 좌표로 반환해주는 api (geocoding api) 와 통신하는 메소드 입니다.
     * https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode (참고)
     * @param address (주소값)
     * @return GeocodeResponseDTO
     */
    public GeocodeResponseDTO callGeocodingAPI(String address) {
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
        String urlBuilder = url + "?query=" + address;

        HttpEntity<String> entity = new HttpEntity<>(createGeocodeHeaders());  // 헤더 생성 메서드 호출

        ResponseEntity<GeocodeResponseDTO> response = restTemplate.exchange(
            urlBuilder,
            HttpMethod.GET,
            entity,
            GeocodeResponseDTO.class
        );

        return response.getBody();
    }

    /**
     * 좌표값 -> 지역 추출 해주는 api (ReverseGeocodingAPI) 와 통신하는 메소드입니다.
     * https://api.ncloud-docs.com/docs/ai-naver-mapsreversegeocoding-gc (참고)
     * @param x (x좌표값)
     * @param y (y좌표값)
     * @return ReverseGeocodeResponseDTO
     */
    public ReverseGeocodeResponseDTO callReverseGeocodingAPI(String x, String y){
        String url = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc";
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(url)
            .queryParam("coords",x+","+y)
            .queryParam("output", "json");
        System.out.println(urlBuilder.toUriString());

        HttpEntity<String> entity = new HttpEntity<>(createReverseGeocodeHeaders());

        ResponseEntity<ReverseGeocodeResponseDTO> response = restTemplate.exchange(
            urlBuilder.toUriString(),
            HttpMethod.GET,
            entity,
            ReverseGeocodeResponseDTO.class
        );

        return response.getBody();
    }

    /**
     * Geocode api 요청 시 필요한 헤더를 만듭니다.
     * 필수값 : 인증ID, 인증PW, 반환형식
     * @return HttpHeaders (만들어진 헤더)
     */
    private HttpHeaders createGeocodeHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ncp-apigw-api-key-id" , clientId);
        headers.set("x-ncp-apigw-api-key", clientSecret);
        headers.set("Accept", "application/json");
        return headers;
    }

    /**
     * Reverse Geocode api 요청 시 필요한 헤더를 만듭니다.
     * 필수값 : 인증ID, 인증PW
     * @return HttpHeaders (만들어진 헤더)
     */
    private HttpHeaders createReverseGeocodeHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ncp-apigw-api-key-id" , clientId);
        headers.set("x-ncp-apigw-api-key", clientSecret);
        return headers;
    }

}
