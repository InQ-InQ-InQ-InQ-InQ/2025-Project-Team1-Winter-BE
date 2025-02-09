package club.inq.team1.client;

import club.inq.team1.dto.response.GeocodeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Component
public class NaverMapClient {

    @Value("${naver.map.client-id}")
    private String client_id;

    @Value("${naver.map.client-secret}")
    private String client_secret;

    private final RestTemplate restTemplate = new RestTemplate();

    //https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode
    //Geocoding API (주소 검색) 호출 클래스
    public GeocodeResponse callGeocodingAPI(String address) {
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
        String urlBuilder = url + "?query=" + address;

        HttpEntity<String> entity = new HttpEntity<>(createHeaders());  // 헤더 생성 메서드 호출

        ResponseEntity<GeocodeResponse> response = restTemplate.exchange(
            urlBuilder,
            HttpMethod.GET,
            entity,
            GeocodeResponse.class
        );

        return response.getBody();
    }

    //https://api.ncloud-docs.com/docs/ai-naver-mapsreversegeocoding-gc
    //ReverseGeoCoding API (좌표 -> 주소) 호출 클래스
    public void callReverseGeocodingAPI(){

    }

    //요청 시 필요한 헤더를 만듭니다 (클라이언트 아이디, 시크릿)
    private HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ncp-apigw-api-key-id" , client_id);
        headers.set("x-ncp-apigw-api-key", client_secret);
        headers.set("Accept", "application/json");
        return headers;
    }

}
