package sg.edu.nus.ad_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPCountDTO {
    private Long id;
    private Integer pendingCount;  // book.status = 0
    private Integer depositedCount;  // book.status = 1
}
