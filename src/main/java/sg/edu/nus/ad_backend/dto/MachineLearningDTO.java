package sg.edu.nus.ad_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineLearningDTO {
    private String Author;
    private String ISBN;
    private String Title;
}
