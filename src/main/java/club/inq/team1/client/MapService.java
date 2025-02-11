package club.inq.team1.client;

import club.inq.team1.dto.response.GeocodeResponseDTO;
import club.inq.team1.dto.response.ReverseGeocodeResponseDTO;

public interface MapService {
    GeocodeResponseDTO callGeocodingAPI(String address);
    ReverseGeocodeResponseDTO callReverseGeocodingAPI(String x, String y);
}
