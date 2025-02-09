package club.inq.team1.client;

import club.inq.team1.dto.response.GeocodeResponseDTO;
import club.inq.team1.dto.response.ReverseGeocodeResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class NaverMapClient {

    @Value("${naver.map.client-id}")
    private String client_id;

    @Value("${naver.map.client-secret}")
    private String client_secret;

    private final RestTemplate restTemplate = new RestTemplate();

    //https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode
    //Geocoding API (주소 검색) 호출 클래스
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

    //https://api.ncloud-docs.com/docs/ai-naver-mapsreversegeocoding-gc
    //ReverseGeoCoding API (좌표 -> 주소) 호출 클래스
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

    //Geocode api 요청 시 필요한 헤더를 만듭니다.
    private HttpHeaders createGeocodeHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ncp-apigw-api-key-id" , client_id);
        headers.set("x-ncp-apigw-api-key", client_secret);
        headers.set("Accept", "application/json");
        return headers;
    }

    //Reverse Geocode api 요청 시 필요한 헤더를 만듭니다.
    private HttpHeaders createReverseGeocodeHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ncp-apigw-api-key-id" , client_id);
        headers.set("x-ncp-apigw-api-key", client_secret);
        return headers;
    }

}
