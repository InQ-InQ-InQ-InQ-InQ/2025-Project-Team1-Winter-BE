package club.inq.team1.dto.request.map;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestMapRangeSearchDTO {
    private BigDecimal leftX;   // leftX     좌측하단 X좌표값 (latitude)
    private BigDecimal rightX;  // rightX    우측상단 X좌표값 (latitude)
    private BigDecimal leftY;   // leftY     좌측하단 Y좌표값 (longitude)
    private BigDecimal rightY;  // rightY    우측상단 Y좌표값 (longitude)
}
