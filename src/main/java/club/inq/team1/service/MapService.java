package club.inq.team1.service;

import club.inq.team1.dto.projection.GeocodeDTO;

public interface MapService {

    //위경도 -> 주소 변환
    String convertAddress(GeocodeDTO addressRequestDTO);

    //주소 -> 위경도 변환
    GeocodeDTO convertGeocode(String address);

    //지역명 변환
    String convertRegion(String address);

    //해당 주소가 있는 주소인지 확인
    boolean searchAddress(String address);
}
