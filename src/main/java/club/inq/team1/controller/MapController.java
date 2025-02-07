package club.inq.team1.controller;

import club.inq.team1.dto.projection.GeocodeDTO;
import club.inq.team1.service.MapService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
@Tag(name = "MapController", description = "지도 관련 기능 API 컨트롤러")
public class MapController {

    private final MapService mapService;

    //ResponseEntity 클래스화 시 개선 필요
    @GetMapping("/convert/address")
    public ResponseEntity<String> convertAddress(@PathVariable double latitude, @PathVariable double longtitude) {
        String address = mapService.convertAddress(new GeocodeDTO(latitude, longtitude));
        if(address != null) {
            return ResponseEntity.ok(address);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/convert/geocode")
    public ResponseEntity<String> convertGeoCode(@PathVariable String address) {
        if(mapService.searchAddress(address)){
            GeocodeDTO geoCodeDTO = mapService.convertGeocode(address);
            if(geoCodeDTO != null) {
                return ResponseEntity.ok().body("latitude: " + geoCodeDTO.getLatitude() + ", longitude: " + geoCodeDTO.getLongitude());
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().body("없는 주소입니다");
    }

    @GetMapping("/convert/region")
    public ResponseEntity<String> convertRegion(@PathVariable String address) {
        String region = mapService.convertRegion(address);
        return ResponseEntity.ok().body(region);
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchAddress(@PathVariable String address) {
        boolean result = mapService.searchAddress(address);
        if(result){
            return ResponseEntity.ok().body("유효한 주소입니다");
        }
        return ResponseEntity.notFound().build();
    }
}
