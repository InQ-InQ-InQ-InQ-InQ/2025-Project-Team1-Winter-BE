package club.inq.team1.service.impl;

import club.inq.team1.dto.projection.GeocodeDTO;
import club.inq.team1.service.MapService;

public class MapServiceImpl implements MapService {

    @Override
    public String convertAddress(GeocodeDTO addressRequestDTO) {
        return "";
    }

    @Override
    public GeocodeDTO convertGeocode(String address) {
        return null;
    }

    @Override
    public String convertRegion(String address) {
        return "";
    }

    @Override
    public boolean searchAddress(String address) {
        return false;
    }
}
