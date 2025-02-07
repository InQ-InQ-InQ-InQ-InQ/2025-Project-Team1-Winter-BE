package club.inq.team1.dto.projection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(title = "위도 경도 값을 담는 dto")
public class GeocodeDTO {

    @Schema(description = "위도 좌표값")
    private double latitude;

    @Schema(description = "경도 좌표값")
    private double longitude;

}
