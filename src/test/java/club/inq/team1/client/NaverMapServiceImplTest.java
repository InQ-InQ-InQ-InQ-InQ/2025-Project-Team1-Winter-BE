package club.inq.team1.client;

import club.inq.team1.dto.response.ResponseGeocodeDTO;
import club.inq.team1.dto.response.ResponseReverseGeocodeDTO;
import club.inq.team1.service.impl.NaverMapServiceImpl;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaverMapServiceTest {

    @Autowired
    NaverMapServiceImpl naverMapClient;

    ResponseGeocodeDTO geocodeResponseDTO ;
    ResponseReverseGeocodeDTO reverseGeocodeResponseDTO ;

    @Test
    @DisplayName("GeoCodingAPI 호출 테스트")
    void callGeocodingAPI() {
        String address = "분당구 불정로 6";
        System.out.println(naverMapClient.callGeocodingAPI(address).getAddresses().get(0).getX());
    }

    @Test
    @DisplayName("RevereseGeocodeAPI 호출 테스트")
    void callReverseGeocodeAPI() {
        String address1 = "경기 수원시 영통구 광교산로 154-42";
        geocodeResponseDTO = naverMapClient.callGeocodingAPI(address1);
        String x1 = geocodeResponseDTO.getAddresses().get(0).getX();
        String y1 = geocodeResponseDTO.getAddresses().get(0).getY();
        reverseGeocodeResponseDTO = naverMapClient.callReverseGeocodingAPI(x1,y1);
        String sido1 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getSido().getName();
        String sigungu1 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getSigugun().getName();

        System.out.println("============================");
        System.out.println("주소 : " + address1);
        System.out.println("좌표 : " + x1 + " / " + y1);
        System.out.println("시도 : " + sido1);
        System.out.println("시군구 : " + sigungu1);
        System.out.println("============================");


        String address2 = "서울 송파구 올림픽로 300";
        geocodeResponseDTO = naverMapClient.callGeocodingAPI(address2);
        String x2 = geocodeResponseDTO.getAddresses().get(0).getX();
        String y2 = geocodeResponseDTO.getAddresses().get(0).getY();
        reverseGeocodeResponseDTO = naverMapClient.callReverseGeocodingAPI(x2,y2);
        String sido2 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getSido().getAlias();
        String sigungu2 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getSigugun().getName();

        System.out.println("============================");
        System.out.println("주소 : " + address2);
        System.out.println("좌표 : " + x2 + " / " + y2);
        System.out.println("시도 : " + sido2);
        System.out.println("시군구 : " + sigungu2);
        System.out.println("============================");


        String address3 = "경북 칠곡군 군청1길 80 칠곡군청";
        geocodeResponseDTO = naverMapClient.callGeocodingAPI(address3);
        String x3 = geocodeResponseDTO.getAddresses().get(0).getX();
        String y3 = geocodeResponseDTO.getAddresses().get(0).getY();
        reverseGeocodeResponseDTO = naverMapClient.callReverseGeocodingAPI(x3,y3);
        String sido3 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getSido().getAlias();
        String sigungu3 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getSigugun().getName();

        System.out.println("============================");
        System.out.println("주소 : " + address3);
        System.out.println("좌표 : " + x3 + " / " + y3);
        System.out.println("시도 : " + sido3);
        System.out.println("시군구 : " + sigungu3);
        System.out.println("============================");
    }

    @Test
    @DisplayName("게시글 작성 시 지역명 파싱 확인")
    void getRegion() {
        BigDecimal latitude = BigDecimal.valueOf(127.1025625);
        BigDecimal longitude = BigDecimal.valueOf(37.5125702);
        String region1 = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 서울 / 실제값 : " + region1);

        latitude = BigDecimal.valueOf(127.0347399);
        longitude = BigDecimal.valueOf(37.3005585);
        String region2 = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 수원 / 실제값 : " + region2);

        latitude = BigDecimal.valueOf(128.4016790);
        longitude = BigDecimal.valueOf(35.9955753);
        String region3 = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 칠곡 / 실제값 : " + region3);
    }
}