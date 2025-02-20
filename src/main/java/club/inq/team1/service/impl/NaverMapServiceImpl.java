package club.inq.team1.service.impl;

import club.inq.team1.dto.response.ResponseGeocodeDTO;
import club.inq.team1.dto.response.ResponseReverseGeocodeDTO;
import club.inq.team1.service.MapService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Naver Map API 와 직접적으로 통신하는 클래스입니다
 */

@Service
public class NaverMapServiceImpl implements MapService {

    @Value("${naver.map.client-id}")
    private String clientId;

    @Value("${naver.map.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String geocodingUrl
        = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
    private final String reverseGeocodingUrl
        = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc";

    /**
     * 주소검색 -> 좌표로 반환해주는 api (geocoding api) 와 통신하는 메소드 입니다.
     * https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode (참고)
     * @param address (주소값)
     * @return GeocodeResponseDTO
     */

    @Override
    public ResponseGeocodeDTO callGeocodingAPI(String address) {
        String urlBuilder = geocodingUrl + "?query=" + address;

        // 헤더 생성 메서드 호출
        HttpHeaders headers = createHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ResponseGeocodeDTO> response = restTemplate.exchange(
            urlBuilder,
            HttpMethod.GET,
            entity,
            ResponseGeocodeDTO.class
        );

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    /**
     * 좌표값 -> 지역 추출 해주는 api (ReverseGeocodingAPI) 와 통신하는 메소드입니다.
     * https://api.ncloud-docs.com/docs/ai-naver-mapsreversegeocoding-gc (참고)
     * @param x (x좌표값)
     * @param y (y좌표값)
     * @return ReverseGeocodeResponseDTO
     */

    @Override
    public ResponseReverseGeocodeDTO callReverseGeocodingAPI(String x, String y){
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(reverseGeocodingUrl)
            .queryParam("coords",x+","+y)
            .queryParam("output", "json");

        //헤더 생성 메소드 호출
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<ResponseReverseGeocodeDTO> response = restTemplate.exchange(
            urlBuilder.toUriString(),
            HttpMethod.GET,
            entity,
            ResponseReverseGeocodeDTO.class
        );

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    /**
     * api 요청 시 필요한 헤더를 만듭니다.
     * 필수값 : 인증ID, 인증PW, (반환형식 -> geocoding 에서만)
     * @return HttpHeaders (만들어진 헤더)
     */

    private HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ncp-apigw-api-key-id" , clientId);
        headers.set("x-ncp-apigw-api-key", clientSecret);
        return headers;
    }

    /**
     * PostService toPost() 메소드에서 지역명 추출 시 사용합니다.
     * @Param latitude
     * @Param longitude
     * @return region (추출된 지역명)
     */

    @Override
    public String getRegion(BigDecimal latitude, BigDecimal longitude) {

        //매개변수를 네이버 api 호출을 위해 String 으로 변경
        String lat = latitude.toString();
        String lng = longitude.toString();

        ResponseReverseGeocodeDTO reverseGeocodeDTO = callReverseGeocodingAPI(lat,lng);

        if(reverseGeocodeDTO != null) {
            String tmp = reverseGeocodeDTO.getResults().get(0)
                .getRegion().getSido().getName();

            //서울, 부산, 대구, 인천, 광주, 대전, 울산, 세종 인 경우 (끝이 시인 경우)
            if(tmp.charAt(tmp.length()-1) == '시'){
                return reverseGeocodeDTO.getResults().get(0).
                    getRegion().getSido().getAlias();
            }

            //경기도, 강원도, 충북, 충남, 전북, 전남, 경북, 경남, 제주 (끝이 도인 경우)
            if(tmp.charAt(tmp.length()-1) == '도'){
                String result = reverseGeocodeDTO.getResults().get(0).
                    getRegion().getSigugun().getName();

                char[] arr = result.toCharArray();
                int idx = 0;
                for (char c : arr) {
                    idx++;
                    if(c == '시' || c == '군') break;
                }

                return result.substring(0, idx-1);
            }
        }
        return null;
    }

}
