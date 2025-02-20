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
class NaverMapServiceImplTest {

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
        String[] arr = {"제주 제주시 공항로 2 제주국제공항"};
        for (String s : arr) {
            String address1 = s;
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
        }
    }

    @Test
    @DisplayName("게시글 작성 시 지역명 파싱 확인")
    void getRegion() {
        String region;
        BigDecimal latitude;
        BigDecimal longitude;

        //서울특별시
        latitude = BigDecimal.valueOf(127.1025625);
        longitude = BigDecimal.valueOf(37.5125702);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 서울 / 실제값 : " + region);

        //부산광역시
        latitude = BigDecimal.valueOf(129.0750223);
        longitude = BigDecimal.valueOf(35.1798160);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 부산 / 실제값 : " + region);

        //대구광역시
        latitude = BigDecimal.valueOf(128.6017630);
        longitude = BigDecimal.valueOf(35.8713900);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 대구 / 실제값 : " + region);

        //인천광역시
        latitude = BigDecimal.valueOf(126.4523468);
        longitude = BigDecimal.valueOf(37.4476110);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 인천 / 실제값 : " + region);

        //광주광역시
        latitude = BigDecimal.valueOf(126.7906620);
        longitude = BigDecimal.valueOf(35.1372122);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 광주 / 실제값 : " + region);

        //대전광역시
        latitude = BigDecimal.valueOf(127.4321036);
        longitude = BigDecimal.valueOf(36.3344179);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 대전 / 실제값 : " + region);

        //울산광역시
        latitude = BigDecimal.valueOf(129.1386316);
        longitude = BigDecimal.valueOf(35.5514992);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 울산 / 실제값 : " + region);

        //세종시
        latitude = BigDecimal.valueOf(127.2652833);
        longitude = BigDecimal.valueOf(36.5042886);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 세종 / 실제값 : " + region);

        //경기도
        latitude = BigDecimal.valueOf(127.0347399);
        longitude = BigDecimal.valueOf(37.3005585);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 수원 / 실제값 : " + region);

        //강원도
        latitude = BigDecimal.valueOf(128.5918400);
        longitude = BigDecimal.valueOf(38.2071690);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 속초 / 실제값 : " + region);

        //경상북도
        latitude = BigDecimal.valueOf(128.4016790);
        longitude = BigDecimal.valueOf(35.9955753);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 칠곡 / 실제값 : " + region);

        //경상남도
        latitude = BigDecimal.valueOf(128.6919403);
        longitude = BigDecimal.valueOf(35.2377974);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 창원 / 실제값 : " + region);

        //전라북도
        latitude = BigDecimal.valueOf(127.1536613);
        longitude = BigDecimal.valueOf(35.8182902);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 전주 / 실제값 : " + region);

        //전라남도
        latitude = BigDecimal.valueOf(126.4631714);
        longitude = BigDecimal.valueOf(34.8161102);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 무안 / 실제값 : " + region);

        //충청북도
        latitude = BigDecimal.valueOf(127.9260120);
        longitude = BigDecimal.valueOf(36.9911050);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 충주 / 실제값 : " + region);

        //충청남도
        latitude = BigDecimal.valueOf(126.6729080);
        longitude = BigDecimal.valueOf(36.6592490);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 홍성 / 실제값 : " + region);

        //제주도
        latitude = BigDecimal.valueOf(126.4959513);
        longitude = BigDecimal.valueOf(33.5059364);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 제주 / 실제값 : " + region);

        latitude = BigDecimal.valueOf(126.5634492);
        longitude = BigDecimal.valueOf(33.2498297);
        region = naverMapClient.getRegion(latitude,longitude);
        System.out.println("기대값 : 서귀포 / 실제값 : " + region);
    }
}