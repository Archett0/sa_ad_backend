package sg.edu.nus.ad_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusByMonthDTO {
    private YearMonth yearMonth;
    private int depositedCount;
    private int completedCount;
    private int rejectedCount;
}
