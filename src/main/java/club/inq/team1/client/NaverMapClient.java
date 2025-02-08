package club.inq.team1.client;

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

    private final RestTemplate restTemplate;

    public NaverMapClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode
    //Geocoding API (주소 검색) 호출 클래스
    public void callGeocodingAPI(){

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
        return headers;
    }

}
