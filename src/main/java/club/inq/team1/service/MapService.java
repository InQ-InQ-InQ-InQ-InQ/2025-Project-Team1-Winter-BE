package club.inq.team1.service;

import club.inq.team1.dto.response.ResponseGeocodeDTO;
import club.inq.team1.dto.response.ResponseReverseGeocodeDTO;

public interface MapService {
    ResponseGeocodeDTO callGeocodingAPI(String address);
    ResponseReverseGeocodeDTO callReverseGeocodingAPI(String x, String y);
}
