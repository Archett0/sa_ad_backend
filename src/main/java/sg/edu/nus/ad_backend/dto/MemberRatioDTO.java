package sg.edu.nus.ad_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRatioDTO {
    private Long id;
    private Integer donationCount;
    private Integer receiveCount;
    private Double ratio;
}
