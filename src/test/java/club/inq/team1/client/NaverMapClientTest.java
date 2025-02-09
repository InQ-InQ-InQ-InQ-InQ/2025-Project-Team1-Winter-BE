package club.inq.team1.client;

import club.inq.team1.dto.response.GeocodeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaverMapClientTest {

    @Autowired
    NaverMapClient naverMapClient = new NaverMapClient();

    @Autowired
    GeocodeResponse geocodeResponse = new GeocodeResponse();

    @Test
    @DisplayName("GeoCodingAPI 호출 테스트")
    void callGeocodingAPI() {
        String address = "분당구 불정로 6";
        System.out.println(naverMapClient.callGeocodingAPI(address).getAddresses().get(0).getX());
    }

}