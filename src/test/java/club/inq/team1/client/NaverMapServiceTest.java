package club.inq.team1.client;

import club.inq.team1.dto.response.GeocodeResponseDTO;
import club.inq.team1.dto.response.ReverseGeocodeResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaverMapServiceTest {

    @Autowired
    NaverMapService naverMapClient;

    GeocodeResponseDTO geocodeResponseDTO ;
    ReverseGeocodeResponseDTO reverseGeocodeResponseDTO ;

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
        String sido1 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getArea1().getName();
        String sigungu1 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getArea2().getName();

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
        String sido2 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getArea1().getName();
        String sigungu2 = reverseGeocodeResponseDTO.getResults().get(0).getRegion().getArea2().getName();

        System.out.println("============================");
        System.out.println("주소 : " + address2);
        System.out.println("좌표 : " + x2 + " / " + y2);
        System.out.println("시도 : " + sido2);
        System.out.println("시군구 : " + sigungu2);
        System.out.println("============================");
    }

}